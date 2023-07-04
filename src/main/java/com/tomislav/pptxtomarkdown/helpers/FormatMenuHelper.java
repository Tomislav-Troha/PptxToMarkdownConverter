package com.tomislav.pptxtomarkdown.helpers;

import com.tomislav.pptxtomarkdown.utils.NotificationManager;
import com.tomislav.pptxtomarkdown.view.PptxToMarkDownView;
import javafx.scene.control.Alert;
import javafx.scene.control.IndexRange;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatMenuHelper {

    public static void selectText(PptxToMarkDownView view, String formatTypeStart, String formatTypeEnd, MenuItem item, String... markdownMark) {
        try {
            String selectedText = view.getMarkdownOutput().getSelectedText().replaceAll(markdownMark.length == 0 ? "" : markdownMark[0], "").trim();
            int selectionStart = view.getMarkdownOutput().getSelection().getStart();
            int selectionEnd = view.getMarkdownOutput().getSelection().getEnd();

            Pattern pattern = Pattern.compile("<p style=\"text-align: (left|right|center);\">(.*)</p>");
            Matcher matcher = pattern.matcher(selectedText);

            if(markdownMark.length != 0){
                if(markdownMark[0].equals("clear")){
                    IndexRange range = view.getMarkdownOutput().getSelection();
                    selectedText = view.getMarkdownOutput().getSelectedText().replaceAll(markdownMark[0], "").trim();
                    String clearedText = selectedText.replaceAll("[*]|_|<u>|</u>|<s>|</s>|<span style=\"background-color: #FFFF00\">|</span>|<sup>|</sup>|<p style=\"text-align: (left;|right;|center;)\">|</p>", "");

                    view.getMarkdownOutput().replaceText(range, clearedText);
                }
            }

            if (selectedText.startsWith(formatTypeStart) && selectedText.endsWith(formatTypeEnd)) return;

            if(selectedText.isEmpty()) return;

            // Check if the selected text contains Markdown-specific characters
            if (selectedText.contains("|") || // for table
                    selectedText.contains("![") || // for images
                    selectedText.contains("$$") || // for math block
                    selectedText.contains("```")  // for code snippet
            ) {
                NotificationManager.showMessageBox("Cannot add " + item.getText(), Alert.AlertType.ERROR, new Duration(2000));
                return;
            }

            String formattedText;
            if (matcher.find()) {
                formattedText = selectedText.replaceAll("<p style=\"text-align: (left|right|center);\">", formatTypeStart);
                formattedText = formattedText.replaceAll("</p>", formatTypeEnd);
            } else {
                formattedText = String.join("", formatTypeStart, selectedText, formatTypeEnd);
            }
            view.getMarkdownOutput().replaceText(selectionStart, selectionEnd, formattedText);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void insertLocalImage(PptxToMarkDownView view) {
        File selectedFile = selectImage();
        if (selectedFile != null) {
            String base64ImageData = convertImageToBase64(selectedFile, 300, 400);

            String fileName = selectedFile.getName();
            int lastPeriodPos = fileName.lastIndexOf('.');
            String extension = "";
            if (lastPeriodPos > 0) {
                extension = fileName.substring(lastPeriodPos+1);
            }
            String markdownFormat = "![](data:%s;base64,%s)";

            String imageTag = String.format(markdownFormat, extension, base64ImageData);

            view.getMarkdownOutput().appendText("\n" + imageTag);
        }
    }
    private static File selectImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp")
        );

        return fileChooser.showOpenDialog(null);
    }
    private static String convertImageToBase64(File selectedFile, int width, int height) {
        try {
            BufferedImage originalImage = ImageIO.read(selectedFile);
            Image tmp = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, "png", baos);
            byte[] bytes = baos.toByteArray();

            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void decreaseImageSize(PptxToMarkDownView view) {

    }
}
