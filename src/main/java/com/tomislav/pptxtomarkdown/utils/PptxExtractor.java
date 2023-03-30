package com.tomislav.pptxtomarkdown.utils;

import com.tomislav.pptxtomarkdown.model.PptxMetadata;
import org.apache.poi.sl.usermodel.TextShape;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextShape;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PptxExtractor {

    public PptxMetadata extractMetadata(String filePath) throws IOException {
        PptxMetadata metadata = new PptxMetadata();

        try (InputStream inputStream = new FileInputStream(filePath);
             XMLSlideShow ppt = new XMLSlideShow(inputStream)) {

            // Extract slide count
            int slideCount = ppt.getSlides().size();
            metadata.setSlideCount(slideCount);

            // Extract author
            String author = ppt.getProperties().getCoreProperties().getCreator();
            metadata.setAuthor(author);

            // Extract title
            String title = ppt.getProperties().getCoreProperties().getTitle();
            metadata.setTitle(title);

            //Extract subtitles
            List<String> subtitle = extractSubtitles(filePath);
            metadata.setSubtitle(subtitle);

            //Extract text slide by slide
            List<List<String>> slideText = extractTextSlideBySlide(filePath);
            metadata.setSlideText(slideText);

        }

        return metadata;
    }


    public static List<String> extractSubtitles(String filePath) throws IOException {
        List<String> titles = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             XMLSlideShow ppt = new XMLSlideShow(fis)) {

            List<XSLFSlide> slides = ppt.getSlides();
            for (int i = 1; i < slides.size(); i++) { // Start from index 1 to skip the first slide
                XSLFSlide slide = slides.get(i);
                for (XSLFShape shape : slide.getShapes()) {
                    if (shape instanceof XSLFTextShape) {
                        XSLFTextShape textShape = (XSLFTextShape) shape;
                        // Check if the text shape is a title placeholder
                        if (textShape.isPlaceholder() && textShape.getTextPlaceholder() == TextShape.TextPlaceholder.TITLE) {
                            titles.add(textShape.getText());
                        }
                    }
                }
            }
        }
        return titles;
    }

    public static List<List<String>> extractTextSlideBySlide(String filePath) throws IOException {
        List<List<String>> slideTexts = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             XMLSlideShow ppt = new XMLSlideShow(fis)) {

            List<XSLFSlide> slides = ppt.getSlides();
            for (int i = 1; i < slides.size(); i++) {
                List<String> slideText = new ArrayList<>();
                XSLFSlide slide = slides.get(i);
                for (XSLFShape shape : slide.getShapes()) {
                    if (shape instanceof XSLFTextShape) {
                        XSLFTextShape textShape = (XSLFTextShape) shape;
                        if(textShape.getTextPlaceholder() != TextShape.TextPlaceholder.TITLE){
                            slideText.add(textShape.getText());
                        }
                    }
                }
                slideTexts.add(slideText);
            }
        }

        return slideTexts;
    }
}

