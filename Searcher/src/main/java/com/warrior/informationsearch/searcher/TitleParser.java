package com.warrior.informationsearch.searcher;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

/**
 * Created by warrior on 08.03.15.
 */
public class TitleParser extends DefaultHandler {

    private static final String TITLE = "title";

    private boolean inTitleTag;
    private StringBuilder title = new StringBuilder();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        inTitleTag = TITLE.equals(qName);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (inTitleTag) {
            if (title.length() > 0) {
                title.append('\n');
            }
            title.append(ch, start, length);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        inTitleTag = false;
    }

    public String getTitle() {
        return title.toString();
    }

    public static String parse(String filename) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        TitleParser parser = new TitleParser();
        try {
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(filename, parser);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return parser.getTitle();
    }
}
