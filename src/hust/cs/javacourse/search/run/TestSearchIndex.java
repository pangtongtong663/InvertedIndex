package hust.cs.javacourse.search.run;

import hust.cs.javacourse.search.index.impl.Term;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.query.AbstractHit;
import hust.cs.javacourse.search.query.AbstractIndexSearcher;
import hust.cs.javacourse.search.query.Sort;
import hust.cs.javacourse.search.query.impl.IndexSearcher;
import hust.cs.javacourse.search.query.impl.SimpleSorter;
import hust.cs.javacourse.search.util.Config;

import javax.swing.plaf.nimbus.AbstractRegionPainter;

/**
 * 测试搜索
 */
public class TestSearchIndex {
    /**
     *  搜索程序入口
     * @param args ：命令行参数
     */
    public static void main(String[] args){
        Sort simpleSorter = new SimpleSorter();
        String indexFile = Config.INDEX_DIR + "index.dat";
        AbstractIndexSearcher indexSearcher = new IndexSearcher();
        indexSearcher.open(indexFile);

        AbstractHit[] hits = indexSearcher.search(new Term("fff"), new Term("ggg"), simpleSorter, AbstractIndexSearcher.LogicalCombination.AND); //coronavirus
        for (AbstractHit hit : hits) {
            System.out.println(hit);
        }


    }
}
