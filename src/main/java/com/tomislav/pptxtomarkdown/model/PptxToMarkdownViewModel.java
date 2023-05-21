package com.tomislav.pptxtomarkdown.model;

import com.tomislav.pptxtomarkdown.utils.MarkdownToHtmlConverter;
import com.tomislav.pptxtomarkdown.view.PptxToMarkDownView;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;

import java.io.File;
import java.io.Serializable;

public class PptxToMarkdownViewModel {

    public PptxToMarkdownViewModel(){

    }
    private String title;
    private TextArea markdownOutput;
    private WebView htmlPreview;
    private ProgressBar progressBar = null;
    private BooleanProperty modified;
    private BooleanProperty markdownFileLoaded;
    private BooleanProperty pptxFileLoaded;
    private Label markdownLabel;
    private Label htmlLabel;
    private File saveLocation = null;
    private BorderPane root;
    public final MarkdownToHtmlConverter htmlConverter = new MarkdownToHtmlConverter();


    //Properties
    //setters and getters for markdown label
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public BorderPane getRoot() {
        return root;
    }
    public void setRoot(BorderPane root) {
        this.root = root;
    }
    public TextArea getMarkdownOutput() {
        return markdownOutput;
    }
    public void setMarkdownOutput(TextArea markdownOutput) {
        this.markdownOutput = markdownOutput;
    }

    //setters and getters for html preview
    public WebView getHtmlPreview() {
        return htmlPreview;
    }
    public void setHtmlPreview(WebView htmlPreview) {
        this.htmlPreview = htmlPreview;
    }

    //setters and getters for progress bar
    public ProgressBar getProgressBar() {
        if (progressBar == null) {
            progressBar = new ProgressBar();
            progressBar.setMaxWidth(Double.MAX_VALUE);
            progressBar.setVisible(false);
        }
        return progressBar;
    }
    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    //setters and getters for modified
    public BooleanProperty getModified() {
        return modified;
    }
    public void setModified(BooleanProperty modified) {
        this.modified = modified;
    }

    //setters and getters for markdownFileLoaded
    public BooleanProperty getMarkdownFileLoaded() {
        return markdownFileLoaded;
    }
    public void setMarkdownFileLoaded(BooleanProperty markdownFileLoaded) {
        this.markdownFileLoaded = markdownFileLoaded;
    }

    //setters and getters for pptxFileLoaded
    public BooleanProperty getPptxFileLoaded() {
        return pptxFileLoaded;
    }
    public void setPptxFileLoaded(BooleanProperty pptxFileLoaded) {
        this.pptxFileLoaded = pptxFileLoaded;
    }

    //setters and getters for save location
    public void setSaveLocation(File saveLocation) {
        this.saveLocation = saveLocation;
    }
    public File getSaveLocation() {
        return saveLocation;
    }

    //setters and getters for labels
    public Label getMarkdownLabel() {
        return markdownLabel;
    }
    public void setMarkdownLabel(Label markdownLabel) {
        this.markdownLabel = markdownLabel;
    }

    //setters and getters for labels
    public Label gethtmlLabel() {
        return htmlLabel;
    }
    public void setHtmlLabel(Label htmlLabel) {
        this.htmlLabel = htmlLabel;
    }

}
