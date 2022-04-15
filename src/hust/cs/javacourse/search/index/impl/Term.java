package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractTerm;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Term extends AbstractTerm {

    public Term() {
        super();
    }

    public Term(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object obj) {
        Term term = (Term) obj;
        return this.getContent().equals(term.getContent());
    }

    @Override
    public String toString() {
        return this.content;
    }

    @Override
    public String getContent() {
        return this.content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int compareTo(AbstractTerm o) {
        return this.getContent().compareTo(o.getContent());
    }

    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            out.writeObject(this.content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readObject(ObjectInputStream in) {
        try {
            this.content = (String) (in.readObject());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
