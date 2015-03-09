package com.warrior.informationsearch.searcher;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by warrior on 08.03.15.
 */
public class TitleParserTest {

    private static final String[] RESOURCES_NAMES = {"files/jrc31958D1006_01-en.xml", "files/jrc31958Q1101-en.xml", "files/jrc31958R0001-en.xml", "files/jrc31958R0003_01-en.xml"};
    private static final String[] TITLES = {"JRC-ACQUIS 31958D1006(01) English",
                                            "JRC-ACQUIS 31958Q1101 English\n" + "EAEC Council: The Statutes of the Euratom Supply Agency",
                                            "JRC-ACQUIS 31958R0001 English\n" + "EEC Council: Regulation No 1 determining the languages to be used by the European Economic Community",
                                            "JRC-ACQUIS 31958R0003(01) English"};

    @Test
    public void titleParserTest() {
        for (int i = 0; i < RESOURCES_NAMES.length; i++) {
            String filename = getResourceFile(RESOURCES_NAMES[i]);
            String title = TitleParser.parse(filename);
            Assert.assertEquals(TITLES[i], title);
        }
    }

    public static String getResourceFile(String name) {
        return TitleParserTest.class.getClassLoader().getResource(name).getFile();
    }
}
