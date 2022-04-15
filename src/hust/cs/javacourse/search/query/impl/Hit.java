package hust.cs.javacourse.search.query.impl;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.query.AbstractHit;

import java.util.Map;

public class Hit extends AbstractHit {
    public Hit() {
        super();
    }

    public Hit(int docId, String docPath) {
        super(docId, docPath);
    }

    public Hit(int docId, String docPath, Map<AbstractTerm, AbstractPosting> termPostingMapping) {
        super(docId, docPath, termPostingMapping);
    }
    @Override
    public int getDocId() {
        return super.docId;
    }

    @Override
    public String getDocPath() {
        return super.docPath;
    }

    @Override
    public String getContent() {
        return super.content;
    }

    @Override
    public void setContent(String content) {
        super.content = content;
    }

    @Override
    public double getScore() {
        return super.score;
    }

    @Override
    public void setScore(double score) {
        super.score = score;
    }

    @Override
    public Map<AbstractTerm, AbstractPosting> getTermPostingMapping() {
        return super.termPostingMapping;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("docId : ").append(getDocId()).append("\n");
        sb.append("docPath : ").append(getDocPath()).append("\n");
        sb.append("content :\n").append(getContent()).append("\n");
        for (Map.Entry<AbstractTerm, AbstractPosting> entry : getTermPostingMapping().entrySet()) {
            sb.append(entry.getKey()).append("\n");
            sb.append(entry.getValue());
        }
        sb.append("\n\n");
        return sb.toString();
    }

    @Override
    public int compareTo(AbstractHit o) {
        return (int) Math.round(getScore() - o.getScore());
    }
}
