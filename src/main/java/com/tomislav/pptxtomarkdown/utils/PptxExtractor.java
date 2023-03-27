package com.tomislav.pptxtomarkdown.utils;

import com.tomislav.pptxtomarkdown.model.PptxMetadata;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PptxExtractor {

    public PptxMetadata extractMetadata(String filePath) throws IOException {
        PptxMetadata metadata = new PptxMetadata();

        try (InputStream inputStream = new FileInputStream(filePath);
             XMLSlideShow ppt = new XMLSlideShow(inputStream)) {

            // Ekstrakcija broja slajdova
            int slideCount = ppt.getSlides().size();
            metadata.setSlideCount(slideCount);

            // Ekstrakcija autora
            String author = ppt.getProperties().getCoreProperties().getCreator();
            metadata.setAuthor(author);

            // Ekstrakcija naslova
            String title = ppt.getProperties().getCoreProperties().getTitle();
            metadata.setTitle(title);

            // Dodajte ekstrakciju drugih metapodataka prema potrebi
        }

        return metadata;
    }
}

