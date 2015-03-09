package com.warrior.informationsearch.indexer;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created by warrior on 08.03.15.
 */
public class ParserTest {

    private static final String[] TEST1_WORDS = {"aaa", "bbb", "ccc", "ddd", "1234", "5678", "has", "decided"};
    @Test
    public void parserTest() {
        File file = new File(getClass().getClassLoader().getResource("file1.xml").getPath());
        List<String> words = Parser.parse(file);
        Assert.assertEquals(Arrays.asList(TEST1_WORDS), words);
    }
}
