package com.findwise;

public class StdIndexEntry implements IndexEntry {
    private String id;
    private double score;

    public StdIndexEntry() {}

    public StdIndexEntry(String id, double score) {
        this.id = id;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
