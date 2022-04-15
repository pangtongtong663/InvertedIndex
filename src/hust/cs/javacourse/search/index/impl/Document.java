package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractTermTuple;

import java.util.*;

public class Document extends AbstractDocument {

    private Set<Integer> set;

    public Document() {
        super();
        set = new HashSet<>();
    }

    public Document(int docId, String path) {
        super(docId, path);
        set = new HashSet<>();
    }

    public Document(int docId, String path, List<AbstractTermTuple> tuples) {
        super(docId, path, tuples);
        set = new HashSet<>();
        for (AbstractTermTuple abstractTermTuple : tuples) {
            set.add(abstractTermTuple.curPos);
        }
    }

    @Override
    public int getDocId() {
        return super.docId;
    }

    @Override
    public void setDocId(int docId) {
        super.docId = docId;
    }

    @Override
    public String getDocPath() {
        return super.docPath;
    }

    @Override
    public void setDocPath(String docPath) {
        super.docPath = docPath;
    }

    @Override
    public List<AbstractTermTuple> getTuples() {
        return super.tuples;
    }

    @Override
    public void addTuple(AbstractTermTuple tuple) {
        if (!set.contains(tuple.curPos)) {
            super.tuples.add(tuple);
            set.add(tuple.curPos);
        }
    }

    @Override
    public boolean contains(AbstractTermTuple tuple) {
        return set.contains(tuple.curPos);
    }

    @Override
    public AbstractTermTuple getTuple(int index) {
        return super.tuples.get(index);
    }

    @Override
    public int getTupleSize() {
        return super.tuples.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("docId : ").append(super.docId).append("\n");
        sb.append("docPath : ").append(super.docPath).append("\n");
        for (AbstractTermTuple tuple : super.tuples) {
            sb.append(tuple);
        }
        return sb.toString();
    }

}
