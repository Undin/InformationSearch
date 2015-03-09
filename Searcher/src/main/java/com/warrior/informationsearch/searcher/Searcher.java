package com.warrior.informationsearch.searcher;

import com.warrior.informationsearch.converter.IndexConverter;
import com.warrior.informationsearch.searcher.tree.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by warrior on 08.03.15.
 */
public class Searcher {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Searcher requires path to inverted index file");
            return;
        }
        if (args.length == 1) {
            System.err.println("Searcher requires query");
            return;
        }
        new Searcher().run(args[0], args[1]);
    }

    private void run(String indexFilename, String query) {
        List<String> filePaths = new ArrayList<>();
        Map<String, List<Integer>> invertedIndex = IndexConverter.xmlToIndex(indexFilename, filePaths);
        if (invertedIndex == null) {
            return;
        }
        Tree queryTree = Tree.parse(query);
        if (queryTree == null) {
            System.out.println("incorrect query");
            return;
        }
        List<Integer> searchResult = queryTree.search(invertedIndex);
        for (Integer docId : searchResult) {
            System.out.println(filePaths.get(docId));
            System.out.println(TitleParser.parse(filePaths.get(docId)));
        }
    }
}
