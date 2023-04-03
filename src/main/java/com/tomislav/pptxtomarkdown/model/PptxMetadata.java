package com.tomislav.pptxtomarkdown.model;

import java.util.List;

public class PptxMetadata {

    private String title;
    private String author;
    private String subtitlesAndText;
    private
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
            String subtitlesAndText,
            int slideCount
    )
    {
        this.title = title;
        this.author = author;
        this.subtitlesAndText = subtitlesAndText;
        this.slideCount = slideCount;
    }


    public String getSubtitlesAndText() {
        return subtitlesAndText;
    }

    public void setSubtitlesAndText(String subtitlesAndText) {
        this.subtitlesAndText = subtitlesAndText;
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

