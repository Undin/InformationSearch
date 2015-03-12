package com.warrior.informationsearch.converter;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created by warrior on 08.03.15.
 */
public class IndexConverter {

    public static final String INVERTED_INDEX = "invertedIndex";
    public static final String FILES = "files";
    public static final String FILE = "file";
    public static final String INDEX = "index";
    public static final String TERM = "term";
    public static final String NAME = "name";
    public static final String ID = "id";

    public static boolean indexToXml(String filename, List<File> files, Map<String, List<Integer>> invertedIndex) {
        try (PrintWriter writer = new PrintWriter(filename, "UTF-8")) {
            XMLStreamWriter xmlWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(writer);
            xmlWriter.writeStartDocument("UTF-8", "1.0");
            xmlWriter.writeStartElement(INVERTED_INDEX);
            xmlWriter.writeStartElement(FILES);
            for (File file : files) {
                xmlWriter.writeStartElement(FILE);
                xmlWriter.writeCharacters(file.getAbsolutePath());
                xmlWriter.writeEndElement();
            }
            xmlWriter.writeEndElement();
            xmlWriter.writeStartElement(INDEX);
            for (String term : invertedIndex.keySet()) {
                xmlWriter.writeStartElement(TERM);
                xmlWriter.writeAttribute(NAME, term);
                List<Integer> ids = invertedIndex.get(term);
                for (Integer docId : ids) {
                    xmlWriter.writeStartElement(ID);
                    xmlWriter.writeCharacters(String.valueOf(docId));
                    xmlWriter.writeEndElement();
                }
                xmlWriter.writeEndElement();
            }
            xmlWriter.writeEndElement();
            xmlWriter.writeEndElement();
            xmlWriter.writeEndDocument();
            xmlWriter.close();

        } catch (FileNotFoundException | XMLStreamException  | UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static Map<String, List<Integer>> xmlToIndex(String filename, List<String> files) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        IndexParser parser = new IndexParser();
        try {
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(filename, parser);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        if (files != null) {
            files.addAll(parser.getFiles());
        }
        return parser.getInvertedIndex();
    }
}
