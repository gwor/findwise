package com.findwise;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CmdFront {

    private final SearchEngine searchEngine;
    private List<String> docList;
    private final Scanner scanner = new Scanner(System.in);
    private final DecimalFormat df = new DecimalFormat("0.00");

    public CmdFront(SearchEngine searchEngine) {
        this.searchEngine = searchEngine;
    }

    public void createTestData() {
        docList = new ArrayList<>();
        docList.add("the brown fox jumped over the brown dog");
        docList.add("the lazy brown dog sat in the corner");
        docList.add("the red fox bit the lazy dog");
    }

    public void runIndex() {
        for (int i = 0; i < docList.size(); i++) {
            searchEngine.indexDocument("Document " + (i + 1), docList.get(i));
        }
    }

    public void runSearch() {
        System.out.println("Basic search engine - type text and press ENTER to run query, type only ENTER to quit");
        String line;

        while (true) {
            System.out.println("Search for:");
            line = scanner.nextLine();
            if (line.equals("")) {
                System.out.println("Quit");
                break;
            }
            System.out.println("Result for \"" + line + "\":");
            List<IndexEntry> resultList = searchEngine.search(line);
            System.out.println("Found " + resultList.size() + " entries");
            for (IndexEntry entry: resultList) {
                System.out.println(entry.getId() + " - " + df.format(entry.getScore()));
            }
        }
    }

    public static void main(String[] args) {
        CmdFront cmdFront = new CmdFront(new BasicSearchEngine());
        cmdFront.createTestData();
        cmdFront.runIndex();
        cmdFront.runSearch();
    }
}
