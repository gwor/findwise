package com.findwise;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.util.List;

public class BasicSearchEngineTest {

    private BasicSearchEngine searchEngine;
    private final DecimalFormat df = new DecimalFormat("0.00");

    @BeforeEach
    void setUp() {
        searchEngine = new BasicSearchEngine();
        searchEngine.indexDocument("Document 1", "the brown fox jumped over the brown dog");
        searchEngine.indexDocument("Document 2", "the lazy brown dog sat in the corner");
        searchEngine.indexDocument("Document 3", "the red fox bit the lazy dog");
    }

    @Test
    void testNoMatchSearch() {
        List<IndexEntry> result = searchEngine.search("test");
        assertEquals(0, result.size());
    }

    @Test
    void testOneMatchSearch() {
        List<IndexEntry> result = searchEngine.search("red");
        assertEquals(1, result.size());
        assertEquals("Document 3", result.get(0).getId());
        assertEquals("0.16", df.format(result.get(0).getScore()));
    }

    @Test
    void testMultipleMatchSearch() {
        List<IndexEntry> result = searchEngine.search("brown");
        assertEquals(2, result.size());
        assertEquals("Document 1", result.get(0).getId());
        assertEquals("0.10", df.format(result.get(0).getScore()));
        assertEquals("Document 2", result.get(1).getId());
        assertEquals("0.05", df.format(result.get(1).getScore()));

        result = searchEngine.search("fox");
        assertEquals(2, result.size());
        assertEquals("Document 3", result.get(0).getId());
        assertEquals("0.06", df.format(result.get(0).getScore()));
        assertEquals("Document 1", result.get(1).getId());
        assertEquals("0.05", df.format(result.get(1).getScore()));
    }

    @Test
    void testAllMatchSearch() {
        List<IndexEntry> result = searchEngine.search("dog");
        assertEquals(3, result.size());
        assertEquals("Document 3", result.get(0).getId());
        assertEquals("0.14", df.format(result.get(0).getScore()));
        assertEquals("Document 1", result.get(1).getId());
        assertEquals("0.12", df.format(result.get(1).getScore()));
        assertEquals("Document 2", result.get(2).getId());
        assertEquals("0.12", df.format(result.get(2).getScore()));
    }
}
