package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.index.impl.Term;
import hust.cs.javacourse.search.index.impl.TermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleScanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Locale;

public class TermTupleScanner extends AbstractTermTupleScanner {
    private int i;
    private String[] terms;
    private int cnt;

    public TermTupleScanner() {
        super();
        i = 0;
        cnt = 0;
        terms = null;
    }

    public TermTupleScanner(BufferedReader input) {
        super(input);
        i = 0;
        cnt = 0;
        terms = null;
    }

    @Override
    public AbstractTermTuple next() {
        try {
            if (cnt == 0) {
                String content = super.input.readLine();
                while (content != null) {
                    if (content.equals("")) {
                        content = super.input.readLine();
                    } else {
                        break;
                    }
                }
                if (content == null) {
                    return null;
                }
                content = content.toLowerCase();
                terms = content.trim().split("\\s+");
            }
            int len = terms[cnt].length();
            if (terms[cnt].charAt(len - 1) == ',' || terms[cnt].charAt(len - 1) == '.') {
                terms[cnt] = terms[cnt].substring(0, len - 1);
            }
            Term term = new Term(terms[cnt]);
            TermTuple tuple = new TermTuple(i, term);
            i++;
            cnt++;
            if (cnt == terms.length) {
                cnt = 0;
            }
            return tuple;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
