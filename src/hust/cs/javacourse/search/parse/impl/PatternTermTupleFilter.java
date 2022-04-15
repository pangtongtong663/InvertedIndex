package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.index.impl.TermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.util.Config;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternTermTupleFilter extends AbstractTermTupleFilter {

    private  String splitRegex;
    private  Pattern pattern;
    private  Matcher match = null;

    public  PatternTermTupleFilter(AbstractTermTupleStream input) {
        super(input);
        this.splitRegex = Config.TERM_FILTER_PATTERN;
        this.pattern = Pattern.compile(splitRegex);
    }

    public PatternTermTupleFilter(AbstractTermTupleStream input, String regex) {
        super(input);
        this.splitRegex = regex;
        this.pattern = Pattern.compile(splitRegex);
    }

    public void setSplitRegex(String splitRegex) {
        this.splitRegex = splitRegex;
        pattern = Pattern.compile(splitRegex);
    }

    @Override
    public AbstractTermTuple next() {
        TermTuple tuple = (TermTuple) super.input.next();
        if (tuple == null) {
            return tuple;
        }

        if (splitRegex == null) {
            return tuple;
        }
        match = pattern.matcher(tuple.term.getContent());
        while (tuple != null && !match.matches()) {
            tuple = (TermTuple) super.input.next();
            if (tuple != null) {
                match = pattern.matcher(tuple.term.getContent());
            }
        }
        return tuple;
    }

}
