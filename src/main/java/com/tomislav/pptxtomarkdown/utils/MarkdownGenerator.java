package com.tomislav.pptxtomarkdown.utils;
import com.tomislav.pptxtomarkdown.model.PptxMetadata;

import java.util.List;

public class MarkdownGenerator {

    public String generateMarkdown(PptxMetadata metadata) {
        StringBuilder markdown = new StringBuilder();

        // Add title
        if (metadata.getTitle() != null && !metadata.getTitle().isEmpty()) {
            markdown.append("# ").append(metadata.getTitle()).append("\n\n");
        }

//        // Add author
//        if (metadata.getAuthor() != null && !metadata.getAuthor().isEmpty()) {
//            markdown.append("Autor: ").append(metadata.getAuthor()).append("\n\n");
//        }
//
//        // Add slide count
//        markdown.append("Broj slajdova: ").append(metadata.getSlideCount()).append("\n\n");

        // Add subtitles and text
        if (metadata.getSubtitlesAndText() != null && !metadata.getSubtitlesAndText().isEmpty()) {
            markdown.append(metadata.getSubtitlesAndText());
        }

        return markdown.toString();
    }

}
