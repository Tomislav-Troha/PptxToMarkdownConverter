package com.tomislav.pptxtomarkdown.view;

import com.tomislav.pptxtomarkdown.utils.MarkdownToHtmlConverter;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;

import java.awt.print.Paper;

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
        htmlPreview.setPrefSize(210, 297);
    }

    public Scene createScene() {
        HBox fileInputLayout = new HBox(10, chooseFileButton, filePathTextField);
        VBox markdownLayout = new VBox(10, new Label("Markdown izlaz:"), markdownOutput);
        VBox htmlLayout = new VBox(10, new Label("HTML pregled:"), htmlPreview);

        SplitPane splitPane = new SplitPane(markdownLayout, htmlLayout);
        splitPane.setDividerPositions(0.5);

        BorderPane root = new BorderPane();
        root.setTop(fileInputLayout);
        root.setCenter(splitPane);

        Scene scene = new Scene(root);


        return scene;
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
