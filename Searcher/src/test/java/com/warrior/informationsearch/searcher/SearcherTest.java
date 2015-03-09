package com.warrior.informationsearch.searcher;

import com.warrior.informationsearch.converter.IndexConverter;
import com.warrior.informationsearch.searcher.tree.Tree;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * Created by warrior on 08.03.15.
 */
public class SearcherTest {

    private Map<String, List<Integer>> invertedIndex;

    @Before
    public void prepareIndex() {
        String filename = getClass().getClassLoader().getResource("inverted_index.xml").getFile();
        invertedIndex = IndexConverter.xmlToIndex(filename, null);
    }

    @Test
    public void searcherTest1() {
        String query = "Bureau";
        Tree queryTree = Tree.parse(query);
        List<Integer> result = queryTree.search(invertedIndex);
        Assert.assertEquals(Arrays.asList(3), result);
    }

    @Test
    public void searcherTest2() {
        String query = "Bureau | ERHARD";
        Tree queryTree = Tree.parse(query);
        List<Integer> result = queryTree.search(invertedIndex);
        Assert.assertEquals(new HashSet<>(Arrays.asList(0, 3)), new HashSet<>(result));
    }

    @Test
    public void searcherTest3() {
        String query = "European & Economic & Community";
        Tree queryTree = Tree.parse(query);
        List<Integer> result = queryTree.search(invertedIndex);
        Assert.assertEquals(new HashSet<>(Arrays.asList(0, 2)), new HashSet<>(result));
    }

    @Test
    public void searcherTest4() {
        String query = "(Bureau | ERHARD) | (European & Economic & Community)";
        Tree queryTree = Tree.parse(query);
        List<Integer> result = queryTree.search(invertedIndex);
        Assert.assertEquals(new HashSet<>(Arrays.asList(0, 2, 3)), new HashSet<>(result));
    }

    @Test
    public void searcherTest5() {
        String query = "(Bureau | ERHARD) & (European & Economic & Community)";
        Tree queryTree = Tree.parse(query);
        List<Integer> result = queryTree.search(invertedIndex);
        Assert.assertEquals(new HashSet<>(Arrays.asList(0)), new HashSet<>(result));
    }

    @Test
    public void searcherTest6() {
        String query = "European & Regulation | Economic & Community";
        Tree queryTree = Tree.parse(query);
        List<Integer> result = queryTree.search(invertedIndex);
        Assert.assertEquals(new HashSet<>(Arrays.asList(0, 1, 2, 3)), new HashSet<>(result));
    }
}
