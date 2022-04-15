package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractPosting;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class Posting extends AbstractPosting {

    public Posting() {
        super();
    }

    public Posting(int docId, int freq, List<Integer> positions) {
        super(docId, freq, positions);
    }



    @Override
    public boolean equals(Object obj) {
        Posting posting = (Posting) obj;
        if (super.docId == posting.getDocId() && super.freq == posting.getFreq() && super.positions.size() == posting.getPositions().size()) {
            posting.sort();
            this.sort();
            Iterator<Integer> iterator1 = posting.getPositions().iterator();
            Iterator<Integer> iterator2 = this.getPositions().iterator();
            while (iterator1.hasNext()) {
                if (!iterator1.next().equals(iterator2.next())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("docId : ").append(getDocId()).append("\n");
        sb.append("freq : ").append(getFreq()).append("\n");
        this.sort();
        sb.append("positions : ");
        for (Integer position : getPositions()) {
            sb.append(position).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("\n");
        return sb.toString();
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
    public int getFreq() {
        return super.freq;
    }

    @Override
    public void setFreq(int freq) {
        super.freq = freq;

    }

    @Override
    public List<Integer> getPositions() {
        return super.positions;
    }

    @Override
    public void setPositions(List<Integer> positions) {
        super.positions = positions;
    }

    @Override
    public int compareTo(AbstractPosting o) {
        return this.getDocId() - o.getDocId();
    }

    @Override
    public void sort() {
        Collections.sort(super.positions);
    }

    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            out.writeObject(super.docId);
            out.writeObject(super.freq);
            out.writeObject(super.positions);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void readObject(ObjectInputStream in) {
        try {
            super.docId = (int) (in.readObject());
            super.freq = (int) (in.readObject());
            super.positions = (List<Integer>)(in.readObject());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
