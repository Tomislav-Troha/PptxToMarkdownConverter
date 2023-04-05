package com.tomislav.pptxtomarkdown.view;

import com.tomislav.pptxtomarkdown.css.Fonts;
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
    private final MenuButton fontMenuButton;
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

        //loop through fonts enum and add menu items
        fontMenuButton = new MenuButton("Odaberi font");
        for(Fonts font : Fonts.values()){
            MenuItem menuItem = new MenuItem(font.getFontName());
            fontMenuButton.getItems().add(menuItem);
        }

        //set action for menu items
        fontMenuButton.getItems().forEach(item -> {
            item.setOnAction(event -> {
                String fontName = item.getText();
                fontMenuButton.setText(fontName);
                updateHtmlPreview(markdownOutput.getText(), fontName);
            });
        });

        //update html preview when text in markdownOutput changes
        markdownOutput.textProperty().addListener((observable, oldValue, newValue) -> updateHtmlPreview(newValue));

        htmlPreview = new WebView();
        htmlPreview.setPrefSize(794, 1123);
    }

    public Scene createScene() {

        HBox fileInputLayout = new HBox(10, chooseFileButton, filePathTextField, fontMenuButton);
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

    //function for updating html preview
    public void updateHtmlPreview(String markdown, String... fontName) {
        WebEngine webEngine = htmlPreview.getEngine();
        String html = MarkdownToHtmlConverter.convertMarkdownToHtml(markdown, fontName.length >= 1 ? fontName[0]: Fonts.ARIAL.getFontName());
        webEngine.loadContent(html);
    }

    // Getters for all view elements
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
