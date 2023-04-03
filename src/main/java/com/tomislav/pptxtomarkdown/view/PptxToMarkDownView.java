package com.tomislav.pptxtomarkdown.view;

import com.tomislav.pptxtomarkdown.utils.MarkdownToHtmlConverter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
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
        markdownOutput.setPrefSize(794, 1123);
        markdownOutput.setPromptText("Markdown izlaz");
        markdownOutput.setEditable(true);

        markdownOutput.textProperty().addListener((observable, oldValue, newValue) -> updateHtmlPreview(newValue));

        htmlPreview = new WebView();
        htmlPreview.setPrefSize(794, 1123);
    }

    public Scene createScene() {

        HBox fileInputLayout = new HBox(10, chooseFileButton, filePathTextField);
        VBox markdownLayout = new VBox(10, new Label("Markdown izlaz:"), markdownOutput);
        VBox htmlLayout = new VBox(10, new Label("HTML pregled:"), htmlPreview);

        SplitPane splitPane = new SplitPane(markdownLayout, htmlLayout);
        //splitPane.addEventFilter(MouseEvent.MOUSE_DRAGGED, event -> event.consume());
        splitPane.setDividerPositions(0.5);

        BorderPane root = new BorderPane();
        root.setTop(fileInputLayout);
        root.setCenter(splitPane);


        return new Scene(root);
    }

    public void updateHtmlPreview(String markdown) {
        WebEngine webEngine = htmlPreview.getEngine();
        String html = MarkdownToHtmlConverter.convertMarkdownToHtml(markdown);
        webEngine.loadContent(html);
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


}
