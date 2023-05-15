package com.tomislav.pptxtomarkdown.view;

import com.tomislav.pptxtomarkdown.css.Fonts;
import com.tomislav.pptxtomarkdown.helpers.MainViewHelper;
import com.tomislav.pptxtomarkdown.helpers.MenuCreatorHelper;
import com.tomislav.pptxtomarkdown.utils.MarkdownToHtmlConverter;
import com.tomislav.pptxtomarkdown.utils.NotificationManager;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class PptxToMarkDownView {

//    private final MenuButton fontMenuButton;
    private final TextArea markdownOutput;
    private final WebView htmlPreview;
    private ProgressBar progressBar;
    BooleanProperty modified = new SimpleBooleanProperty(false);
    private final Label markdownLabel;
    private final Label htmlLabel;
    File saveLocation = null;
    MarkdownToHtmlConverter htmlConverter = new MarkdownToHtmlConverter();

    public PptxToMarkDownView() {

        markdownLabel = new Label("Markdown");
        markdownLabel.setStyle("-fx-font-size: 20px;");

        htmlLabel = new Label("Markdown preview");
        htmlLabel.setStyle("-fx-font-size: 20px;");

        markdownOutput = new TextArea();
        markdownOutput.setPrefSize(794, 1123);
        markdownOutput.setPromptText("Markdown view");
        markdownOutput.setEditable(true);

        //loop through fonts enum and add menu items
//        fontMenuButton = new MenuButton("Odaberi font");
//        for(Fonts font : Fonts.values()){
//            MenuItem menuItem = new MenuItem(font.getFontName());
//            fontMenuButton.getItems().add(menuItem);
//        }
//
//        //set action for menu items
//        fontMenuButton.getItems().forEach(item -> {
//            item.setOnAction(event -> {
//                String fontName = item.getText();
//                fontMenuButton.setText(fontName);
//                updateHtmlPreview(markdownOutput.getText(), fontName);
//            });
//        });

        //update html preview when text in markdownOutput changes
        markdownOutput.textProperty().addListener((observable, oldValue, newValue) -> updateHtmlPreview(newValue));

        htmlPreview = new WebView();
        htmlPreview.setPrefSize(794, 1123);
    }

    public Scene createScene(PptxToMarkDownView view) {
        //create main view
        MenuCreatorHelper menuCreatorHelper = new MenuCreatorHelper();
        MenuBar menuBar = menuCreatorHelper.createMenuBar(view);

        HBox fileInputLayout = new HBox(10);
        VBox markdownLayout = MainViewHelper.createLayout(markdownLabel, markdownOutput);
        VBox htmlLayout = MainViewHelper.createLayout(htmlLabel, htmlPreview);

        SplitPane splitPane = new SplitPane(markdownLayout, htmlLayout);
        splitPane.setDividerPositions(0.5);

        VBox topLayout = new VBox(menuBar, fileInputLayout); // Combine the menuBar and fileInputLayout

        BorderPane root = new BorderPane();
        root.setTop(topLayout);
        root.setCenter(splitPane);
        root.setBottom(getProgressBar());

        //if the markdownOutput is modified, set the modified flag to true
        markdownOutput.textProperty().addListener((observable, oldValue, newValue) -> {
            modified.set(true);
        });

        //if the markdownOutput is modified, set the markdownLabel text to "Markdown *" to indicate that the file is modified
        modified.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                markdownLabel.setText("Markdown *");
                markdownLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");
            } else {
                markdownLabel.setText("Markdown");
                markdownLabel.setStyle("-fx-font-weight: normal; -fx-font-size: 20px;");
            }
        });

        //if the markdown is not saved, show a dialog to save the markdown file
        root.setOnKeyPressed(event -> {
            if(event.isControlDown() && event.getCode() == KeyCode.S){
                    if(modified.get()){
                        String markdown = markdownOutput.getText();
                        if(saveLocation == null){
                            FileChooser fileChooser = new FileChooser();
                            fileChooser.setTitle("Save Markdown");
                            fileChooser.getExtensionFilters().addAll(
                                    new FileChooser.ExtensionFilter("Markdown", "*.md"),
                                    new FileChooser.ExtensionFilter("All Files", "*.*"));
                            saveLocation = fileChooser.showSaveDialog(new Stage());
                        }
                        if(saveLocation != null){
                            try {
                                Files.writeString(saveLocation.toPath(), markdown);

                                modified.set(false); // reset the modified flag
                            } catch (IOException e) {
                                NotificationManager.showMessageBox("Error saving markdown file " + e.getMessage(), Alert.AlertType.ERROR, new Duration(3));
                            }
                        }
                    }
            }
        });

        return new Scene(root);
    }


    //function for updating html preview
    public void updateHtmlPreview(String markdown, String @NotNull ... fontName) {
        WebEngine webEngine = htmlPreview.getEngine();
        String cssFileUrl = getClass().getResource("/markdown-preview.css").toExternalForm();
        webEngine.setUserStyleSheetLocation(cssFileUrl);
        String html = htmlConverter.convertMarkdownToHtml(markdown, fontName.length >= 1 ? fontName[0]: Fonts.ARIAL.getFontName());
        webEngine.loadContent(html);
    }

    // Getters for all view elements
    public TextArea getMarkdownOutput() {
        return markdownOutput;
    }

    public WebView getHtmlPreview() {
        return htmlPreview;
    }

    public ProgressBar getProgressBar() {
        if (progressBar == null) {
            progressBar = new ProgressBar();
            progressBar.setMaxWidth(Double.MAX_VALUE);
            progressBar.setVisible(false);
        }
        return progressBar;
    }

}
