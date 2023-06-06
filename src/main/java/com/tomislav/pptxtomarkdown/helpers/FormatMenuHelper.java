package com.tomislav.pptxtomarkdown.helpers;

import com.tomislav.pptxtomarkdown.utils.NotificationManager;
import com.tomislav.pptxtomarkdown.view.PptxToMarkDownView;
import javafx.scene.control.Alert;
import javafx.scene.control.IndexRange;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class FormatMenuHelper {

    public static void selectText(PptxToMarkDownView view, String formatTypeStart, String formatTypeEnd, String... markdownMark) {
        try {
            if(markdownMark.length != 0){
                if(markdownMark[0].equals("clear")){
                    IndexRange range = view.getMarkdownOutput().getSelection();
                    String selectedText = view.getMarkdownOutput().getSelectedText().replaceAll(markdownMark[0], "").trim();
                    String clearedText = selectedText.replaceAll("[*]|_|<u>|</u>|<s>|</s>|<span style=\"background-color: #FFFF00\">|</span>|<sup>|</sup>|<p style=\"text-align: left\">|</p>", "");
                    view.getMarkdownOutput().replaceText(range, clearedText);
                }
            }

            String selectedText = view.getMarkdownOutput().getSelectedText().replaceAll(markdownMark.length == 0 ? "" : markdownMark[0], "").trim();
            int selectionStart = view.getMarkdownOutput().getSelection().getStart();
            int selectionEnd = view.getMarkdownOutput().getSelection().getEnd();

            // Replace the selected text with a Markdown heading of the corresponding level
            String formattedText = String.join("", formatTypeStart, selectedText, formatTypeEnd);

            view.getMarkdownOutput().replaceText(selectionStart, selectionEnd, formattedText);
        }
        catch (Exception e) {
            NotificationManager.showMessageBox(e.getMessage(), Alert.AlertType.ERROR, new Duration(3000));
        }

    }


    public static void insertLocalImage(PptxToMarkDownView view) {
        File selectedFile = selectImage();
        if (selectedFile != null) {
            String base64ImageData = convertImageToBase64(selectedFile);
            String imageDataInMarkdown = String.format("<img src=\"data:image/png;base64,%s\" alt=\"image\" style=\"display: block; margin: auto;\">", base64ImageData);
            view.getMarkdownOutput().appendText("\n" + imageDataInMarkdown);
        }
    }

    private static File selectImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp")
        );

        return fileChooser.showOpenDialog(null);
    }

    private static String convertImageToBase64(File selectedFile) {
        try {
            byte[] fileContent = Files.readAllBytes(selectedFile.toPath());
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void insertImage(PptxToMarkDownView view) {

    }
}
