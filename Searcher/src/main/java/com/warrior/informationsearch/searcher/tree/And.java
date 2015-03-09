package com.warrior.informationsearch.searcher.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by warrior on 08.03.15.
 */
class And extends Tree {

    private final Tree left;
    private final Tree right;

    public And(Tree left, Tree right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public List<Integer> search(Map<String, List<Integer>> invertedIndex) {
        List<Integer> leftResult = left.search(invertedIndex);
        List<Integer> rightResult = right.search(invertedIndex);
        return intersect(leftResult, rightResult);
    }

    private static List<Integer> intersect(List<Integer> first, List<Integer> second) {
        List<Integer> result = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (i != first.size() && j != second.size()) {
            int docId1 = first.get(i);
            int docId2 = second.get(j);
            if (docId1 == docId2) {
                result.add(docId1);
                i++;
                j++;
            } else if (docId1 < docId2) {
                i++;
            } else {
                j++;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "( " + left.toString() + " & " + right.toString() + " )";
    }
}
