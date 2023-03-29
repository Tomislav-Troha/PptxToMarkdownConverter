package com.tomislav.pptxtomarkdown.view;

import com.tomislav.pptxtomarkdown.utils.MarkdownToHtmlConverter;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;

public class PptxToMarkDownView {

    private final FileChooser fileChooser;
    private final Button chooseFileButton;
    private final TextField filePathTextField;
    private final TextArea markdownOutput;
    private final WebView htmlPreview;

    public PptxToMarkDownView() {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PowerPoint Files", "*.pptx"));

        chooseFileButton = new Button("Odaberi pptx datoteku");
        filePathTextField = new TextField();
        filePathTextField.setEditable(false);
        filePathTextField.setPromptText("Odabrana datoteka");

        markdownOutput = new TextArea();
        markdownOutput.setPromptText("Markdown izlaz");
        markdownOutput.setEditable(false);

        htmlPreview = new WebView();
    }

    public Scene createScene() {
        HBox fileInputLayout = new HBox(10, chooseFileButton, filePathTextField);
        VBox mainLayout = new VBox(10, fileInputLayout, markdownOutput, htmlPreview);
        mainLayout.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setTop(mainLayout);


        return new Scene(root, 800, 600);
    }

    // Getters za kontroler

    public WebView getHtmlPreview() {
        return htmlPreview;
    }

    public FileChooser getFileChooser() {
        return fileChooser;
    }

    public Button getChooseFileButton() {
        return chooseFileButton;
    }

    public TextField getFilePathTextField() {
        return filePathTextField;
    }

    public TextArea getMarkdownOutput() {
        return markdownOutput;
    }

    public void updateHtmlPreview(String markdown) {
        WebEngine webEngine = htmlPreview.getEngine();
        String html = MarkdownToHtmlConverter.convertMarkdownToHtml(markdown);
        webEngine.loadContent(html);
    }

    private String markdownToHtml(String markdown) {
        // Ovdje možete koristiti bilo koju biblioteku za konverziju Markdown u HTML
        // ili implementirati vlastitu logiku.
        // Za ovaj primjer, samo ćemo zamijeniti znakove novog reda s <br> oznakama.

        return markdown.replace("\n", "<br>");
    }
}
