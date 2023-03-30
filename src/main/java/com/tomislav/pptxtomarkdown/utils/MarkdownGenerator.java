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

        // Add subtitles
        if (metadata.getSubtitle() != null && !metadata.getSubtitle().isEmpty()) {
            markdown.append("## ").append(metadata.getSubtitle()).append("\n\n");
        }

        //Add slide text
        if (metadata.getSlideText() != null && !metadata.getSlideText().isEmpty()) {
            for (List<String> slideText : metadata.getSlideText()) {
                for (String text : slideText) {
                    markdown.append(text).append("\n\n");
                }
            }
        }


        return markdown.toString();
    }
}
