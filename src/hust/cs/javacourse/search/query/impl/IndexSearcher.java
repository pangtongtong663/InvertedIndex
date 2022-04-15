package hust.cs.javacourse.search.query.impl;

import hust.cs.javacourse.search.index.AbstractIndex;
import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.index.impl.Posting;
import hust.cs.javacourse.search.index.impl.PostingList;
import hust.cs.javacourse.search.query.AbstractHit;
import hust.cs.javacourse.search.query.AbstractIndexSearcher;
import hust.cs.javacourse.search.query.Sort;
import hust.cs.javacourse.search.util.FileUtil;

import java.io.File;
import java.util.*;

public class IndexSearcher extends AbstractIndexSearcher {

    public void setIndex(AbstractIndex index) {
        super.index = index;
    }

    @Override
    public void open(String indexFile) {
        File file = new File(indexFile);
        super.index.load(file);
    }

    @Override
    public AbstractHit[] search(AbstractTerm queryTerm, Sort sorter) {
        PostingList postings = (PostingList) super.index.termToPostingListMapping.get(queryTerm);
        List<AbstractHit> hits = new ArrayList<>();
        for (AbstractPosting posting : postings.getPostings()) {
            Map<AbstractTerm, AbstractPosting> termPostingMapping = new HashMap<>();
            termPostingMapping.put(queryTerm, posting);
            Hit hit = new Hit(posting.getDocId(), super.index.docIdToDocPathMapping.get(posting.getDocId()), termPostingMapping);
            sorter.score(hit);
            hits.add(hit);
        }
        sorter.sort(hits);
        AbstractHit[] arr1 = (AbstractHit[]) hits.toArray(new AbstractHit[0]);
        AbstractHit[] arr2 = new AbstractHit[arr1.length];
        for (int i = 0; i < arr1.length; i++) {
            arr2[i] = arr1[arr1.length - 1 - i];
        }
        return arr2;
    }

    @Override
    public AbstractHit[] search(AbstractTerm queryTerm1, AbstractTerm queryTerm2, Sort sorter, LogicalCombination combine) {
        PostingList postings1 = (PostingList) super.index.termToPostingListMapping.get(queryTerm1);
        PostingList postings2 = (PostingList) super.index.termToPostingListMapping.get(queryTerm2);

        if (combine == LogicalCombination.AND) {
            postings1.sort();
            postings2.sort();
            List<AbstractHit> hits = new ArrayList<>();
            ListIterator<AbstractPosting> iterator1 = postings1.getPostings().listIterator();
            ListIterator<AbstractPosting> iterator2 = postings2.getPostings().listIterator();

            while (iterator1.hasNext() && iterator2.hasNext()) {
                AbstractPosting posting1 = iterator1.next();
                AbstractPosting posting2 = iterator2.next();
                if (posting1.getDocId() < posting2.getDocId()) {
                    iterator2.previous();
                } else if (posting1.getDocId() > posting2.getDocId()) {
                    iterator1.previous();
                } else {
                    Map<AbstractTerm, AbstractPosting> termPostingMapping = new HashMap<>();
                    termPostingMapping.put(queryTerm1, posting1);
                    termPostingMapping.put(queryTerm2, posting2);
                    Hit hit = new Hit(posting1.getDocId(), super.index.docIdToDocPathMapping.get(posting1.getDocId()), termPostingMapping);
                    sorter.score(hit);
                    hits.add(hit);
                }
            }
            sorter.sort(hits);
            AbstractHit[] arr1 = (AbstractHit[]) hits.toArray(new AbstractHit[0]);
            AbstractHit[] arr2 = new AbstractHit[arr1.length];
            for (int i = 0; i < arr1.length; i++) {
                arr2[i] = arr1[arr1.length - 1 - i];
            }
            return arr2;
        } else {
            postings1.sort();
            postings2.sort();
            List<AbstractHit> hits = new ArrayList<>();
            ListIterator<AbstractPosting> iterator1 = postings1.getPostings().listIterator();
            ListIterator<AbstractPosting> iterator2 = postings2.getPostings().listIterator();

            while (iterator1.hasNext() && iterator2.hasNext()) {
                AbstractPosting posting1 = iterator1.next();
                AbstractPosting posting2 = iterator2.next();
                if (posting1.getDocId() < posting2.getDocId()) {
                    Map<AbstractTerm, AbstractPosting> termPostingMapping = new HashMap<>();
                    termPostingMapping.put(queryTerm1, posting1);
                    Hit hit = new Hit(posting1.getDocId(), super.index.docIdToDocPathMapping.get(posting1.getDocId()), termPostingMapping);
                    sorter.score(hit);
                    hits.add(hit);
                    iterator2.previous();
                } else if (posting1.getDocId() > posting2.getDocId()) {
                    Map<AbstractTerm, AbstractPosting> termPostingMapping = new HashMap<>();
                    termPostingMapping.put(queryTerm2, posting2);
                    Hit hit = new Hit(posting2.getDocId(), super.index.docIdToDocPathMapping.get(posting2.getDocId()), termPostingMapping);
                    sorter.score(hit);
                    hits.add(hit);
                    iterator1.previous();
                } else {
                    Map<AbstractTerm, AbstractPosting> termPostingMapping = new HashMap<>();
                    termPostingMapping.put(queryTerm1, posting1);
                    termPostingMapping.put(queryTerm2, posting2);
                    Hit hit = new Hit(posting1.getDocId(), super.index.docIdToDocPathMapping.get(posting1.getDocId()), termPostingMapping);
                    sorter.score(hit);
                    hits.add(hit);
                }
            }
            while (iterator1.hasNext()) {
                AbstractPosting posting1 = iterator1.next();
                Map<AbstractTerm, AbstractPosting> termPostingMapping = new HashMap<>();
                termPostingMapping.put(queryTerm1, posting1);
                Hit hit = new Hit(posting1.getDocId(), super.index.docIdToDocPathMapping.get(posting1.getDocId()), termPostingMapping);
                sorter.score(hit);
                hits.add(hit);
            }

            while (iterator2.hasNext()) {
                AbstractPosting posting2 = iterator2.next();
                Map<AbstractTerm, AbstractPosting> termPostingMapping = new HashMap<>();
                termPostingMapping.put(queryTerm2, posting2);
                Hit hit = new Hit(posting2.getDocId(), super.index.docIdToDocPathMapping.get(posting2.getDocId()), termPostingMapping);
                sorter.score(hit);
                hits.add(hit);
            }
            sorter.sort(hits);
            AbstractHit[] arr1 = (AbstractHit[]) hits.toArray(new AbstractHit[0]);
            AbstractHit[] arr2 = new AbstractHit[arr1.length];
            for (int i = 0; i < arr1.length; i++) {
                arr2[i] = arr1[arr1.length - 1 - i];
            }
            return arr2;
        }
    }
}
