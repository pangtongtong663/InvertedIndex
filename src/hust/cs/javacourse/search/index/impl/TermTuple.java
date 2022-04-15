package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.index.AbstractTermTuple;

public class TermTuple extends AbstractTermTuple {

    public TermTuple() {
        super();
        this.curPos = 0;
        this.term = null;
    }

    public TermTuple(int curPos, AbstractTerm term) {
        this.curPos = curPos;
        this.term = term;
    }

    @Override
    public boolean equals(Object obj) {
        TermTuple tuple = (TermTuple) obj;
        return this.curPos == tuple.curPos && this.term.equals(tuple.term);

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nfreq : 1\n").append("curPos : ").append(this.curPos).append("\n");
        sb.append(this.term).append("\n");
        return sb.toString();
    }
}
