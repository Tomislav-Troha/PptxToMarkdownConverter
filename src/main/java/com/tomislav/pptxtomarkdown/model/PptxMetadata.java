package com.tomislav.pptxtomarkdown.model;

import java.util.List;

public class PptxMetadata {

    private String title;
    private String author;
    private List<String> subtitles;
    private List<List<String>> slideText;
    private int slideCount;

    // Dodajte druge metapodatke koje želite pohraniti

    // Konstruktor bez argumenata
    public PptxMetadata() {
    }

    // Konstruktor s argumentima
    public PptxMetadata
    (
            String title,
            String author,
            List<String> subtitles,
            List<List<String>> slideText,
            int slideCount
    )
    {
        this.title = title;
        this.author = author;
        this.subtitles = subtitles;
        this.slideText = slideText;
        this.slideCount = slideCount;
    }

    public List<List<String>> getSlideText() {
        return slideText;
    }

    public void setSlideText(List<List<String>> slideText) {
        this.slideText = slideText;
    }

    public List<String> getSubtitle() {
        return subtitles;
    }

    public void setSubtitle(List<String> subtitles) {
        this.subtitles = subtitles;
    }

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

