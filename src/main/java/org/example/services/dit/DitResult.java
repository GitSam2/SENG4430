package org.example.services.dit;

public class DitResult {
    // class attributes
    int rank;
    int DIT;
    String className;
    String notes;

    // constructors
    public DitResult(int rank, int DIT, String className, String notes) {
        this.rank = rank;
        this.DIT = DIT;
        this.className = className;
    }

    // getters and setters
    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getDIT() {
        return DIT;
    }

    public void setDIT(int DIT) {
        this.DIT = DIT;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
