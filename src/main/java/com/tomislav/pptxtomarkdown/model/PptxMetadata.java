package com.tomislav.pptxtomarkdown.model;

import java.util.List;

public class PptxMetadata {

    private String title;
    private String author;
    private String extractedMetadata;
    private int slideCount;

    // Konstruktor bez argumenata
    public PptxMetadata() {
    }

    // Konstruktor s argumentima
    public PptxMetadata
    (
            String title,
            String author,
            String extractedMetadata,
            int slideCount
    )
    {
        this.title = title;
        this.author = author;
        this.extractedMetadata = extractedMetadata;
        this.slideCount = slideCount;
    }


    public String getExtractedMetadata() {
        return extractedMetadata;
    }

    public void setExtractedMetadata(String extractedMetadata) {
        this.extractedMetadata = extractedMetadata;
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

