package com.tomislav.pptxtomarkdown.view;

import com.tomislav.pptxtomarkdown.css.Fonts;
import com.tomislav.pptxtomarkdown.helpers.MainViewHelper;
import com.tomislav.pptxtomarkdown.helpers.MenuCreatorHelper;
import com.tomislav.pptxtomarkdown.model.PptxToMarkdownViewModel;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PptxToMarkDownView extends PptxToMarkdownViewModel {

    public PptxToMarkDownView() {
        initializeMarkdownLabel();
        initializeHtmlLabel();
        initializeMarkdownOutput();
        initializeHtmlPreview();
        initializeProperties();
    }

    private void initializeMarkdownLabel() {
        setMarkdownLabel(new Label());
        getMarkdownLabel().setText("Markdown ");
        getMarkdownLabel().setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        getMarkdownLabel().setPadding(new Insets(10, 0, 0, 0)); // 10 pixels of padding to the top

    }
    private void initializeHtmlLabel() {
        setHtmlLabel(new Label());
        gethtmlLabel().setText("Markdown preview");
        gethtmlLabel().setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        gethtmlLabel().setPadding(new Insets(10, 0, 0, 0)); // 10 pixels of padding to the top
    }
    private void initializeMarkdownOutput() {
        setMarkdownOutput(new TextArea());
        getMarkdownOutput().setPrefSize(794, 1123);
        getMarkdownOutput().setStyle("-fx-font-size: 18px;");
        getMarkdownOutput().setPromptText("Type markdown here...");
        getMarkdownOutput().setEditable(true);

        getMarkdownOutput().textProperty().addListener((observable, oldValue, newValue) -> updateHtmlPreview(newValue));
    }
    private void initializeHtmlPreview() {
        setHtmlPreview(new WebView());
        getHtmlPreview().setPrefSize(794, 1123);
    }
    private void initializeProperties() {
        setModified(new SimpleBooleanProperty(false));
        setPptxFileLoaded(new SimpleBooleanProperty(false));
        setMarkdownFileLoaded(new SimpleBooleanProperty(false));
    }

    public Scene createScene(PptxToMarkDownView view) {
        //create menu bar
        MenuBar menuBar = MenuCreatorHelper.createMenuBar(view);

        HBox fileInputLayout = new HBox(10);
        VBox markdownLayout = MainViewHelper.createLayout(getMarkdownLabel(), getMarkdownOutput());
        markdownLayout.setAlignment(javafx.geometry.Pos.CENTER);

        VBox htmlLayout = MainViewHelper.createLayout(gethtmlLabel(), getHtmlPreview());
        htmlLayout.setAlignment(javafx.geometry.Pos.CENTER);

        SplitPane splitPane = new SplitPane(markdownLayout, htmlLayout);
        splitPane.setDividerPositions(0.5);

        VBox topLayout = new VBox(menuBar, fileInputLayout); // Combine the menuBar and fileInputLayout

        setRoot(new BorderPane());
        getRoot().setTop(topLayout);
        getRoot().setCenter(splitPane);
        getRoot().setBottom(getProgressBar());

        //if the markdownOutput is modified, set the modified flag to true
        getMarkdownOutput().textProperty().addListener((observable, oldValue, newValue) -> {
                    getModified().set(true);
            getMarkdownFileLoaded().set(false);
        });

        MainViewHelper.isModified(view);

        return new Scene(getRoot());
    }

    public void updateHtmlPreview(String markdown) {
        WebEngine webEngine = getHtmlPreview().getEngine();
        String cssFileUrl = getClass().getResource("/markdown-preview.css").toExternalForm();
        webEngine.setUserStyleSheetLocation(cssFileUrl);
        String html = htmlConverter.convertMarkdownToHtml(markdown);
        webEngine.loadContent(html);
    }
}