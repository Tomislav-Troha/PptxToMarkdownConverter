package com.tomislav.pptxtomarkdown.utils;
import com.tomislav.pptxtomarkdown.model.PptxMetadata;

public class MarkdownGenerator {

    public String generateMarkdown(PptxMetadata metadata) {
        StringBuilder markdown = new StringBuilder();

        // Dodavanje naslova
        if (metadata.getTitle() != null && !metadata.getTitle().isEmpty()) {
            markdown.append("# ").append(metadata.getTitle()).append("\n\n");
        }

        // Dodavanje autora
        if (metadata.getAuthor() != null && !metadata.getAuthor().isEmpty()) {
            markdown.append("Autor: ").append(metadata.getAuthor()).append("\n\n");
        }

        // Dodavanje broja slajdova
        markdown.append("Broj slajdova: ").append(metadata.getSlideCount()).append("\n\n");

        // Dodajte druge metapodatke prema potrebi

        return markdown.toString();
    }
}
