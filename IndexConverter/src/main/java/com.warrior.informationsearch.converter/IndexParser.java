package com.warrior.informationsearch.converter;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by warrior on 08.03.15.
 */
public class IndexParser extends DefaultHandler {

    private List<String> files = new ArrayList<>();
    private Map<String, List<Integer>> invertedIndex = new HashMap<>();

    private String currentTag;

    private List<Integer> currentResults;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentTag = qName;
        if (IndexConverter.TERM.equals(qName)) {
            String term = attributes.getValue(IndexConverter.NAME);
            currentResults = new ArrayList<>();
            invertedIndex.put(term, currentResults);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (IndexConverter.FILE.equals(currentTag)) {
            String path = new String(ch, start, length);
            files.add(path);
        } else if (IndexConverter.ID.equals(currentTag)) {
            int docId = Integer.parseInt(new String(ch, start, length));
            currentResults.add(docId);
        }
    }

    public List<String> getFiles() {
        return files;
    }

    public Map<String, List<Integer>> getInvertedIndex() {
        return invertedIndex;
    }
}
