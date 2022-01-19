package com.findwise;

import java.util.*;

public class BasicSearchEngine implements SearchEngine {

    // first key - word, second key - doc id, value - tf
    private final Map<String, Map<String, Double>> wordMap = new HashMap<>();
    private int docsSize = 0;

    public void indexDocument(String id, String content) {
        List<String> wordList = getWordList(content.toLowerCase().trim());
        Set<String> processedWordSet = new HashSet<>();
        // significance of one word
        double tfBase = 1.0 / wordList.size();
        Map<String, Double> docsForWordMap;
        for (String word: wordList) {
            docsForWordMap = wordMap.get(word);
            if (docsForWordMap != null) {
                // word already indexed
                if (processedWordSet.contains(word)) {
                    // next occurance in processed doc
                    double newTf = docsForWordMap.get(id) + tfBase;
                    docsForWordMap.put(id, newTf);
                } else {
                    // first occurance in processed doc
                    docsForWordMap.put(id, tfBase);
                    processedWordSet.add(word);
                }
            } else {
                // new word in index
                docsForWordMap = new HashMap<>();
                docsForWordMap.put(id, tfBase);
                wordMap.put(word, docsForWordMap);
                processedWordSet.add(word);
            }
        }
        docsSize++;
    }

    private List<String> getWordList(String content) {
        return Arrays.asList(content.split(" "));
    }

    public List<IndexEntry> search(String term) {
        List<IndexEntry> result = new ArrayList<>();
        Map<String, Double> docsForWordMap = wordMap.get(term.toLowerCase().trim());
        if (docsForWordMap != null) {
            for (String id: docsForWordMap.keySet()) {
                double tfIdf;
                if (docsForWordMap.size() < docsSize) {
                    tfIdf = docsForWordMap.get(id) * Math.log((double)docsSize / docsForWordMap.size());
                } else {
                    // case when log returns 0
                    tfIdf = docsForWordMap.get(id);
                }
                result.add(new StdIndexEntry(id, tfIdf));
            }
        }
        result.sort(Comparator.comparing(IndexEntry::getScore).reversed());

        return result;
    }
}
