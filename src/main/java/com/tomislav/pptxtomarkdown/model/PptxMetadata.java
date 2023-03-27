package com.tomislav.pptxtomarkdown.model;

public class PptxMetadata {

    private String title;
    private String author;
    private int slideCount;

    // Dodajte druge metapodatke koje želite pohraniti

    // Konstruktor bez argumenata
    public PptxMetadata() {
    }

    // Konstruktor s argumentima
    public PptxMetadata(String title, String author, int slideCount) {
        this.title = title;
        this.author = author;
        this.slideCount = slideCount;
    }

    // Getteri i setteri
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getSlideCount() {
        return slideCount;
    }

    public void setSlideCount(int slideCount) {
        this.slideCount = slideCount;
    }

    // Dodajte gettere i settere za druge metapodatke

    // toString metoda za prikaz objekta kao string
    @Override
    public String toString() {
        return "PptxMetadata{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", slideCount=" + slideCount +
                '}';
    }
}

