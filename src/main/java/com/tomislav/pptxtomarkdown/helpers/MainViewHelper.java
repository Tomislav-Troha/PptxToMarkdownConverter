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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class MainViewHelper {

    public static VBox createLayout(Label label, Node outputArea) {
        return new VBox(10, label, outputArea);
    }


    public static void isModified(PptxToMarkDownView view){
        //if the markdownOutput is modified, set the markdownLabel text to "Markdown *" to indicate that the file is modified
        view.getModified().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                view.getMarkdownLabel().setText("Markdown * " + (view.getTitle() != null ? "- " + view.getTitle() : ""));
                view.getMarkdownLabel().setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");
            } else {
                view.getMarkdownLabel().setText("Markdown " + (view.getTitle() != null ? "- " + view.getTitle() : ""));
                view.getMarkdownLabel().setStyle("-fx-font-weight: normal; -fx-font-size: 20px;");
            }
        });
    }

    public static void saveFileListeners(PptxToMarkDownView view){
        // If markdown is modified
        if(view.getModified().get()){
            String markdown = view.getMarkdownOutput().getText();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Markdown");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Markdown", "*.md"),
                    new FileChooser.ExtensionFilter("All Files", "*.*"));

            // If no Markdown file has been previously loaded or saved
            if(view.getSaveLocation() == null){
                view.setSaveLocation(fileChooser.showSaveDialog(new Stage()));
            }

            // Save the modified content to the file
            if(view.getSaveLocation() != null){
                try {
                    Files.writeString(view.getSaveLocation().toPath(), markdown);

                    view.getModified().set(false);
                    // If a Markdown file was loaded and saved, update the markdownFileLoaded flag
                    if (view.getMarkdownFileLoaded().get()) {
//                        view.setTitle(view.getSaveLocation().getName());
                        view.getMarkdownFileLoaded().set(true);
                    }
                    // If a PowerPoint file was loaded and the markdown was saved, update the pptxFileLoaded flag
                    if (view.getPptxFileLoaded().get()) {
//                        view.setTitle(view.getSaveLocation().getName());
                        view.getPptxFileLoaded().set(true);
                    }
                } catch (IOException e) {
                    NotificationManager.showMessageBox("Error in saving file " + e.getMessage(), Alert.AlertType.ERROR, new Duration(3));
                }
            }
        }
    }
    public static void saveAsFileListeners(PptxToMarkDownView view){
        String markdown = view.getMarkdownOutput().getText();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Markdown As");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Markdown", "*.md"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));

        File chosenFile = fileChooser.showSaveDialog(new Stage());

        if(chosenFile != null){
            try {
                Files.writeString(chosenFile.toPath(), markdown);

                view.setSaveLocation(chosenFile);

                view.setTitle(chosenFile.getName());

                view.getModified().set(false);

                if(view.getMarkdownFileLoaded().get()){
                    view.getMarkdownFileLoaded().set(true);
                } else if(view.getPptxFileLoaded().get()){
                    view.getPptxFileLoaded().set(true);
                }
            } catch (IOException e) {
                NotificationManager.showMessageBox("Error in saving file " + e.getMessage(), Alert.AlertType.ERROR, new Duration(3));
            }
        }
    }


}
