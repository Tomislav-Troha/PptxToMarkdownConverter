package com.tomislav.pptxtomarkdown;

import com.tomislav.pptxtomarkdown.model.PptxMetadata;
import com.tomislav.pptxtomarkdown.utils.MarkdownGenerator;
import com.tomislav.pptxtomarkdown.utils.PptxExtractor;

import java.io.IOException;

public class App {

        public static void main(String[] args) {
            // Provjerite jesu li navedeni ulazni i izlazni argumenti
            if (args.length != 1) {
                System.err.println("Koristi: java -jar <naziv_aplikacije>.jar <pptx_datoteka>");
                System.exit(1);
            }

            String inputFilePath = args[0];

            PptxExtractor extractor = new PptxExtractor();
            MarkdownGenerator generator = new MarkdownGenerator();

            try {
                // Ekstrakcija metapodataka iz pptx datoteke
                PptxMetadata metadata = extractor.extractMetadata(inputFilePath);

                // Generiranje Markdown iz metapodataka
                String markdown = generator.generateMarkdown(metadata);

                // Ispis Markdown izlaza na konzolu
                System.out.println(markdown);
            } catch (IOException e) {
                System.err.println("Pogreška prilikom čitanja pptx datoteke:  " + e.getMessage());
                e.printStackTrace();
            }
        }
}
