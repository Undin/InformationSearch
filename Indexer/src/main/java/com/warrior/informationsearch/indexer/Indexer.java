package com.warrior.informationsearch.indexer;

import com.warrior.informationsearch.converter.IndexConverter;

import java.io.File;
import java.util.*;

/**
 * Created by warrior on 05.03.15.
 */
public class Indexer {

    public static final String OUTPUT_FILE_NAME = "inverted_index.xml";

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Indexer requires path to documents");
            return;
        }
        File file = new File(args[0]);
        new Indexer().run(file);
    }

    public void run(File rootFile) {
        List<File> fileList = getAllXmlFiles(rootFile);
        List<Map<String, Integer>> indexes = new ArrayList<>(fileList.size());
        for (File file : fileList) {
            Map<String, Integer> index = indexFile(file);
            indexes.add(index);
        }
        Map<String, List<Integer>> invertedIndex = invertedIndex(indexes);

        int tokens = 0;
        for (Map<String, Integer> index : indexes) {
            tokens += index.size();
        }
        int terms = invertedIndex.size();
        System.out.format("tokens: %d, terms: %d\n", tokens, terms);
        boolean successful = IndexConverter.indexToXml(OUTPUT_FILE_NAME, fileList, invertedIndex);
        if (successful) {
            System.out.println("index has written to " + OUTPUT_FILE_NAME);
        } else {
            System.out.println("error");
        }
    }

    private static Map<String, List<Integer>> invertedIndex(List<Map<String, Integer>> indexes) {
        Map<String, List<Integer>> invertedIndex = new HashMap<>();
        for (int i = 0; i < indexes.size(); i++) {
            Map<String, Integer> index = indexes.get(i);
            for (Map.Entry<String, Integer> entry : index.entrySet()) {
                List<Integer> list = invertedIndex.get(entry.getKey());
                if (list == null) {
                    list = new ArrayList<>();
                    invertedIndex.put(entry.getKey(), list);
                }
                list.add(i);
            }
        }
        return invertedIndex;
    }

    private static Map<String, Integer> indexFile(File file) {
        Map<String, Integer> map = new HashMap<>();
        List<String> tokens = Parser.parse(file);
        for (String token : tokens) {
            Integer f = map.get(token);
            if (f == null) {
                map.put(token, 1);
            } else {
                map.put(token, f + 1);
            }
        }
        return map;
    }

    private static List<File> getAllXmlFiles(File file) {
        if (file.isFile()) {
            if (isXml(file)) {
                return Collections.singletonList(file);
            }
        } else if (file.isDirectory()) {
            List<File> fileList = new ArrayList<>();
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files)
                fileList.addAll(getAllXmlFiles(f));
            }
            return fileList;
        }
        return Collections.emptyList();
    }

    private static boolean isXml(File file) {
        return file.getName().endsWith(".xml");
    }
}
