package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.*;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * AbstractIndex的具体实现类
 */
public class Index extends AbstractIndex {
    public Index() {
        super();
    }
    /**
     * 返回索引的字符串表示
     *
     * @return 索引的字符串表示
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, String> entry : super.docIdToDocPathMapping.entrySet()) {
            sb.append(entry.getKey()).append("   ").append(entry.getValue()).append("\n");
        }
        sb.append("\n\n\n");
        for (Map.Entry<AbstractTerm, AbstractPostingList> entry: super.termToPostingListMapping.entrySet()) {
            sb.append("term : ").append(entry.getKey()).append("\n");
            sb.append(entry.getValue()).append("\n");
        }
        return sb.append("\n").toString();
    }

    /**
     * 添加文档到索引，更新索引内部的HashMap
     *
     * @param document ：文档的AbstractDocument子类型表示
     */
    @Override
    public void addDocument(AbstractDocument document) {
        if (!super.docIdToDocPathMapping.containsKey(document.getDocId())) {
            super.docIdToDocPathMapping.put(document.getDocId(), document.getDocPath());
            for (AbstractTermTuple tuple: document.getTuples()) {
                String content = tuple.term.getContent().toLowerCase();
                tuple.term.setContent(content);
                PostingList postings = null;
                if (!super.termToPostingListMapping.containsKey(tuple.term)) {
                    postings = new PostingList();
                    super.termToPostingListMapping.put(tuple.term, postings);
                }
                 postings = (PostingList) super.termToPostingListMapping.get(tuple.term);
                if (!postings.getMap().containsKey(document.getDocId())) {
                    Posting posting = new Posting();
                    posting.setDocId(document.getDocId());
                    posting.setFreq(1);
                    List<Integer> positions = posting.getPositions();
                    positions.add(tuple.curPos);
                    posting.setPositions(positions);
                    postings.add(posting);
                } else {
                    Posting posting = (Posting) postings.get(postings.indexOf(document.getDocId()));
                    posting.setFreq(posting.getFreq() + 1);
                    List<Integer> positions = posting.getPositions();
                    positions.add(tuple.curPos);
                    posting.setPositions(positions);
                }
            }
        }

    }

    /**
     * <pre>
     * 从索引文件里加载已经构建好的索引.内部调用FileSerializable接口方法readObject即可
     * @param file ：索引文件
     * </pre>
     */
    @Override
    public void load(File file) {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.getPath()));
            readObject(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <pre>
     * 将在内存里构建好的索引写入到文件. 内部调用FileSerializable接口方法writeObject即可
     * @param file ：写入的目标索引文件
     * </pre>
     */
    @Override
    public void save(File file) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file.getPath()));
            writeObject(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回指定单词的PostingList
     *
     * @param term : 指定的单词
     * @return ：指定单词的PostingList;如果索引字典没有该单词，则返回null
     */
    @Override
    public AbstractPostingList search(AbstractTerm term) {
        return super.termToPostingListMapping.get(term);
    }

    /**
     * 返回索引的字典.字典为索引里所有单词的并集
     *
     * @return ：索引中Term列表
     */
    @Override
    public Set<AbstractTerm> getDictionary() {
        return super.termToPostingListMapping.keySet();
    }

    /**
     * <pre>
     * 对索引进行优化，包括：
     *      对索引里每个单词的PostingList按docId从小到大排序
     *      同时对每个Posting里的positions从小到大排序
     * 在内存中把索引构建完后执行该方法
     * </pre>
     */
    @Override
    public void optimize() {
        for (AbstractPostingList postingList : super.termToPostingListMapping.values()) {
            PostingList postings = (PostingList) postingList;
            postings.sort();
            for (AbstractPosting p : postings.getPostings()) {
                Posting posting = (Posting) p;
                List<Integer> positions = posting.getPositions();
                Collections.sort(positions);
                posting.setPositions(positions);
            }
        }
    }

    /**
     * 根据docId获得对应文档的完全路径名
     *
     * @param docId ：文档id
     * @return : 对应文档的完全路径名
     */
    @Override
    public String getDocName(int docId) {
        return super.docIdToDocPathMapping.get(docId);
    }

    /**
     * 写到二进制文件
     *
     * @param out :输出流对象
     */
    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            out.writeObject(super.docIdToDocPathMapping);
            out.writeObject(super.termToPostingListMapping);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从二进制文件读
     *
     * @param in ：输入流对象
     */
    @Override
    public void readObject(ObjectInputStream in) {
        try {
            super.docIdToDocPathMapping = (Map<Integer, String>) (in.readObject());
            super.termToPostingListMapping = (Map<AbstractTerm, AbstractPostingList>) (in.readObject());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
