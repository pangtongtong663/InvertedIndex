package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.index.impl.TermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.util.StopWordsSet;

public class StopWordTermTupleFilter extends AbstractTermTupleFilter {

    public StopWordTermTupleFilter(AbstractTermTupleStream input) {
        super(input);
    }

    @Override
    public AbstractTermTuple next() {
        TermTuple tuple = (TermTuple) super.input.next();
        while (tuple != null && StopWordsSet.STOP_WORDS_SET.contains(tuple.term.getContent())) {
            tuple = (TermTuple) super.input.next();
        }
        return tuple;
    }
}
