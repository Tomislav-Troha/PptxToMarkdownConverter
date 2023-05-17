package com.tomislav.pptxtomarkdown.helpers;

import com.tomislav.pptxtomarkdown.utils.NotificationManager;
import com.tomislav.pptxtomarkdown.view.PptxToMarkDownView;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.nio.file.Files;

public class MainViewHelper {

    public static VBox createLayout(Label label, Node outputArea) {
        return new VBox(10, label, outputArea);
    }

    public static void saveFileListeners(BorderPane root, PptxToMarkDownView view){
        //if the markdownOutput is modified, set the markdownLabel text to "Markdown *" to indicate that the file is modified
        view.getModified().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                view.getMarkdownLabel().setText("Markdown *");
                view.getMarkdownLabel().setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");
            } else {
                view.getMarkdownLabel().setText("Markdown");
                view.getMarkdownLabel().setStyle("-fx-font-weight: normal; -fx-font-size: 20px;");
            }
        });

        //if the markdown is not saved, show a dialog to save the markdown file
        root.setOnKeyPressed(event -> {
            if(event.isControlDown() && event.getCode() == KeyCode.S){
                if(view.getModified().get()){
                    String markdown = view.getMarkdownOutput().getText();
                    if(view.getSaveLocation() == null && !view.getMarkdownFileLoaded().get()){
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Save Markdown");
                        fileChooser.getExtensionFilters().addAll(
                                new FileChooser.ExtensionFilter("Markdown", "*.md"),
                                new FileChooser.ExtensionFilter("All Files", "*.*"));
                        view.setSaveLocation(fileChooser.showSaveDialog(new Stage()));
                    }

                    if(view.getSaveLocation() == null && !view.getPptxFileLoaded().get()){
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Save Markdown");
                        fileChooser.getExtensionFilters().addAll(
                                new FileChooser.ExtensionFilter("Markdown", "*.md"),
                                new FileChooser.ExtensionFilter("All Files", "*.*"));
                        view.setSaveLocation(fileChooser.showSaveDialog(new Stage()));
                    }

                    if(view.getSaveLocation() != null){
                        try {
                            Files.writeString(view.getSaveLocation().toPath(), markdown);

                            view.getModified().set(false); // reset the modified flag
                            view.getMarkdownFileLoaded().set(true); // reset the fileLoaded flag
                        } catch (IOException e) {
                            NotificationManager.showMessageBox("Error saving markdown file " + e.getMessage(), Alert.AlertType.ERROR, new Duration(3));
                        }
                    }
                }
            }
        });
    }
}
