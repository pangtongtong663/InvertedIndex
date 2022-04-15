package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractDocumentBuilder;
import hust.cs.javacourse.search.index.AbstractIndex;
import hust.cs.javacourse.search.index.AbstractIndexBuilder;
import hust.cs.javacourse.search.parse.impl.TermTupleScanner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class IndexBuilder extends AbstractIndexBuilder {
    public IndexBuilder(AbstractDocumentBuilder docBuilder) {
        super(docBuilder);
    }


    @Override
    public AbstractIndex buildIndex(String rootDirectory) {
        try {
            Index index = new Index();
            File file = new File(rootDirectory);
            String[] files = file.list();
            for (String fileName : files) {
                String path = rootDirectory + "\\" + fileName;
                BufferedReader br = new BufferedReader(new FileReader(path));
                Document document = (Document) super.docBuilder.build(super.docId, path, new File(path));
                super.docId++;
                index.addDocument(document);
            }
            return index;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
