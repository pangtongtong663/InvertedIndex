package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractPostingList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class PostingList extends AbstractPostingList {
    private Map<Integer, Integer> map;
    private int i = 0;

    public PostingList() {
        super();
        this.map = new HashMap<>();
    }

    @Override
    public void add(AbstractPosting posting) {
        if (!map.containsKey(posting.getDocId())) {
            super.list.add(posting);
            map.put(posting.getDocId(), i);
            i++;
        }
    }

    public Map<Integer, Integer> getMap() {
        return this.map;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (AbstractPosting posting : super.list) {
            sb.append(posting.toString());
        }
        return sb.toString();
    }

    @Override
    public void add(List<AbstractPosting> postings) {
        for (AbstractPosting posting : postings) {
            this.add(posting);
        }
    }

    public List<AbstractPosting> getPostings() {
        return super.list;
    }

    @Override
    public AbstractPosting get(int index) {
        return super.list.get(index);
    }

    @Override
    public int indexOf(AbstractPosting posting) {
        return super.list.indexOf(posting);
    }

    @Override
    public int indexOf(int docId) {
        return map.get(docId);
    }

    @Override
    public boolean contains(AbstractPosting posting) {
        return map.containsKey(posting.getDocId());
    }

    @Override
    public void remove(int index) {
        AbstractPosting posting = get(index);
        remove(posting);
    }

    @Override
    public void remove(AbstractPosting posting) {
        map.remove(posting.getDocId());
        super.list.remove(posting);
    }

    @Override
    public int size() {
        return super.list.size();
    }

    @Override
    public void clear() {
        super.list.clear();
    }

    @Override
    public boolean isEmpty() {
        return super.list.isEmpty();
    }

    @Override
    public void sort() {
        Collections.sort(super.list, (v1, v2) -> v1.getDocId() - v2.getDocId());
    }

    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            out.writeObject(super.list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readObject(ObjectInputStream in) {
        try {
            super.list = (List<AbstractPosting>) (in.readObject());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
