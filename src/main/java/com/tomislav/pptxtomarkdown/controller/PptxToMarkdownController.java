package com.tomislav.pptxtomarkdown.controller;

import com.tomislav.pptxtomarkdown.model.PptxMetadata;
import com.tomislav.pptxtomarkdown.utils.MarkdownGenerator;
import com.tomislav.pptxtomarkdown.utils.PptxExtractor;
import com.tomislav.pptxtomarkdown.view.PptxToMarkDownView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class PptxToMarkdownController {

    private final PptxToMarkDownView view;
    private final PptxExtractor extractor;
    private final MarkdownGenerator generator;

    public PptxToMarkdownController(PptxToMarkDownView view) {
        this.view = view;
        this.extractor = new PptxExtractor();
        this.generator = new MarkdownGenerator();

        // Dodavanje kontrolera za obradu događaja za chooseFileButton
//        view.getChooseFileButton().setOnAction(event -> {
//
//            File selectedFile = view.getFileChooser().showOpenDialog(new Stage());
//            if (selectedFile != null) {
//                view.getFilePathTextField().setText(selectedFile.getName());
//                try {
//                    PptxMetadata metadata = extractor.extractMetadata(selectedFile.getAbsolutePath());
//                    //PptxMetadata metadata = extractor.extractMetadata("C:\\Users\\tomis\\Desktop\\Upravljanje-znanjem-u-obrazovanju.pptx");
//                    String markdown = generator.generateMarkdown(metadata);
//                    view.getMarkdownOutput().setText(markdown);
//                    view.updateHtmlPreview(markdown);
//                } catch (IOException e) {
//                    view.getMarkdownOutput().setText("Pogreška prilikom citanja pptx datoteke: " + e.getMessage());
//                }
//            }
//        });


    }
}
