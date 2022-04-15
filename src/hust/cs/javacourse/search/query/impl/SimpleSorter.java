package hust.cs.javacourse.search.query.impl;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.query.AbstractHit;
import hust.cs.javacourse.search.query.Sort;

import java.util.Collections;
import java.util.List;

public class SimpleSorter implements Sort {
    @Override
    public void sort(List<AbstractHit> hits) {
        Collections.sort(hits);
    }

    @Override
    public double score(AbstractHit hit) {
        double res = 0.0;
        for (AbstractPosting posting : hit.getTermPostingMapping().values()) {
            res += posting.getFreq();
        }
        hit.setScore(res);
        return res;
    }
}
