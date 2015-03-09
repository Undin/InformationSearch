package com.warrior.informationsearch.indexer;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by warrior on 05.03.15.
 */
public class Parser extends DefaultHandler {

    private static final String BODY = "body";

    private final List<String> words = new ArrayList<>();

    private boolean inBody = false;

    private Parser() {
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (BODY.equals(qName)) {
            inBody = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (BODY.equals(qName)) {
            inBody = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (inBody) {
            StringTokenizer tokenizer = new StringTokenizer(new String(ch, start, length), " \t\n\r,.()\"!?:;");
            while (tokenizer.hasMoreTokens()) {
                words.add(tokenizer.nextToken().toLowerCase());
            }
        }
    }

    public static List<String> parse(File xml) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        Parser parser = new Parser();
        try {
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(xml, parser);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return parser.words;
    }
}
