package com.tomislav.pptxtomarkdown.utils;
import com.tomislav.pptxtomarkdown.helpers.PptxExtractorHelper;
import com.tomislav.pptxtomarkdown.model.PptxMetadata;
import javafx.scene.control.Alert;
import javafx.util.Duration;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.sl.usermodel.SlideShow;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlideShow;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PptxExtractor {

    public PptxMetadata extractMetadata(String filePath) {
        PptxMetadata metadata = new PptxMetadata();

        try (InputStream inputStream = new FileInputStream(filePath)) {
            if (filePath.endsWith(".pptx")) {
                XMLSlideShow ppt = new XMLSlideShow(inputStream);

                // Extract slide count
                int slideCount = ppt.getSlides().size();
                metadata.setSlideCount(slideCount);

                // Extract author
                String author = ppt.getProperties().getCoreProperties().getCreator();
                metadata.setAuthor(author);

                // Extract title
                String title = ppt.getProperties().getCoreProperties().getTitle();
                metadata.setTitle(title);
            } else if (filePath.endsWith(".ppt")) {
                HSLFSlideShow ppt = new HSLFSlideShow(inputStream);

                // Extract slide count
                int slideCount = ppt.getSlides().size();
                metadata.setSlideCount(slideCount);

                // Extract author
                String author = ppt.getSummaryInformation().getAuthor();
                metadata.setAuthor(author);

                // Extract title
                String title = ppt.getSummaryInformation().getTitle();
                metadata.setTitle(title);
            } else {
                throw new Exception("File format not supported.");
            }

            // Extract all metadata
            List<String> subtitleAndText = PptxExtractorHelper.extractMetadata(filePath);
            String output = PptxExtractorHelper.formatMarkdownOutput(subtitleAndText);
            metadata.setExtractedMetadata(output);
        } catch (Exception e) {
            NotificationManager.showMessageBox("",e + " " + e.getMessage(), Alert.AlertType.ERROR, Duration.seconds(3));
        }

        return metadata;
    }
}

