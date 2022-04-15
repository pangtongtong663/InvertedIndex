package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractDocumentBuilder;
import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.parse.impl.LengthTermTupleFilter;
import hust.cs.javacourse.search.parse.impl.PatternTermTupleFilter;
import hust.cs.javacourse.search.parse.impl.StopWordTermTupleFilter;
import hust.cs.javacourse.search.parse.impl.TermTupleScanner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentBuilder extends AbstractDocumentBuilder {
    @Override
    public AbstractDocument build(int docId, String docPath, AbstractTermTupleStream termTupleStream) {
        List<AbstractTermTuple> tuples = new ArrayList<>();
        AbstractTermTuple tuple = termTupleStream.next();
        while (tuple != null) {
            tuples.add(tuple);
            tuple = termTupleStream.next();
        }
        return new Document(docId, docPath, tuples);
    }

    @Override
    public AbstractDocument build(int docId, String docPath, File file) {
        AbstractDocument document = null;
        AbstractTermTupleStream ts = null;
        try {
            ts = new TermTupleScanner(new BufferedReader(new InputStreamReader(new FileInputStream(file))));
            ts = new StopWordTermTupleFilter(ts);
            ts = new PatternTermTupleFilter(ts);
            ts = new LengthTermTupleFilter(ts);
            document = build(docId, docPath, ts);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            ts.close();
        }
        return document;
    }
}
