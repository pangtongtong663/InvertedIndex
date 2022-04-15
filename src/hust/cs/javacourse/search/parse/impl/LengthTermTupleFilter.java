package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.index.impl.TermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.util.Config;

public class LengthTermTupleFilter extends AbstractTermTupleFilter {

    public LengthTermTupleFilter(AbstractTermTupleStream input) {
        super(input);
    }

    @Override
    public AbstractTermTuple next() {
        TermTuple tuple = (TermTuple) super.input.next();
        if (tuple == null) {
            return tuple;
        }
        int len = tuple.term.getContent().length();
        while (tuple != null && (len < Config.TERM_FILTER_MINLENGTH || len > Config.TERM_FILTER_MAXLENGTH)) {
            tuple = (TermTuple) super.input.next();
            if (tuple != null) {
                len = tuple.term.getContent().length();
            }
        }

        return tuple;
    }
}
