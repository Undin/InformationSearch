package com.warrior.informationsearch.searcher.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by warrior on 08.03.15.
 */
class Or extends Tree {

    private final Tree left;
    private final Tree right;

    public Or(Tree left, Tree right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public List<Integer> search(Map<String, List<Integer>> invertedIndex) {
        List<Integer> leftResult = left.search(invertedIndex);
        List<Integer> rightResult = right.search(invertedIndex);
        return union(leftResult, rightResult);
    }

    private static List<Integer> union(List<Integer> first, List<Integer> second) {
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
                result.add(docId1);
                i++;
            } else {
                result.add(docId2);
                j++;
            }
        }
        while (i != first.size()) {
            result.add(first.get(i));
            i++;
        }
        while (j != second.size()) {
            result.add(second.get(j));
            j++;
        }
        return result;
    }


    @Override
    public String toString() {
        return "( " + left.toString() + " | " + right.toString() + " )";
    }
}
