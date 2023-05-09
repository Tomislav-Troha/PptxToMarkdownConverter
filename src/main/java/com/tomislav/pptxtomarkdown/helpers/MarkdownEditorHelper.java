package com.tomislav.pptxtomarkdown.helpers;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MarkdownEditorHelper {

    public static void saveFile(Stage stage, File saveLocation, TextArea markdownView, boolean isSaved) {
        if (saveLocation == null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Markdown Files", "*.md"));
            saveLocation = fileChooser.showSaveDialog(stage);
        }

        if (saveLocation != null) {
            // Save the file
            try (FileWriter writer = new FileWriter(saveLocation)) {
                writer.write(markdownView.getText());
                isSaved = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
