package com.tomislav.pptxtomarkdown.utils;
import com.tomislav.pptxtomarkdown.model.PptxMetadata;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

            //Extract all metadata
            List<String> subtitleAndText = PptxExtractorHelper.extractMetadata(filePath);
            String output = PptxExtractorHelper.formatMarkdownOutput(subtitleAndText);
            metadata.setExtractedMetadata(output);
        }

        return metadata;
    }
}

