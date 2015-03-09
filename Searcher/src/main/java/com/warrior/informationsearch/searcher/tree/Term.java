package com.warrior.informationsearch.searcher.tree;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by warrior on 08.03.15.
 */
class Term extends Tree {

    public final String term;

    public Term(String term) {
        this.term = term;
    }

    @Override
    public List<Integer> search(Map<String, List<Integer>> invertedIndex) {
        List<Integer> list = invertedIndex.get(term);
        if (list == null) {
            list = Collections.emptyList();
        }
        return list;
    }

    @Override
    public String toString() {
        return term;
    }
}
