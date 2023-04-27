package com.tomislav.pptxtomarkdown.utils;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.sl.usermodel.TextShape;
import org.apache.poi.xslf.usermodel.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class PptxExtractorHelper {

    public static List<String> extractMetadata(String filePath) throws IOException {
        List<String> slideContents = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             XMLSlideShow ppt = new XMLSlideShow(fis)) {

            List<XSLFSlide> slides = ppt.getSlides();
            for (int i = 1; i < slides.size(); i++) { // Start from index 1 to skip the first slide
                XSLFSlide slide = slides.get(i);
                StringBuilder stringSlideContents = new StringBuilder();

                for (XSLFShape shape : slide.getShapes()) {
                    if (shape instanceof XSLFTextShape) {
                        XSLFTextShape textShape = (XSLFTextShape) shape;

                        // Check if the text shape is a title placeholder
                        if (textShape.isPlaceholder() && textShape.getTextPlaceholder() == TextShape.TextPlaceholder.TITLE) {
                            stringSlideContents.append("## ").append(textShape.getText());
                        } else {
                            String text = textShape.getText().replaceAll("(?m)^", "- ").replaceAll("\n", "\n\n");
                            stringSlideContents.append("\n\n").append(text);
                        }
                    }

                    //extract images from pptx
                    if (shape instanceof XSLFPictureShape) {
                        //process image
                        String base64Image = processPictureShape((XSLFPictureShape) shape, 350, 300);

                        //get image type
                        String pictureData = ((XSLFPictureShape) shape).getPictureData().getContentType();
                        String cssTag = "<img src=\"data:%s;base64,%s\" alt=\"image\" style=\"display: block; margin: auto;\">";
                        String imageTag = String.format(cssTag, pictureData, base64Image);

                        stringSlideContents.append("\n\n").append(imageTag);
                    }
                }
                slideContents.add(stringSlideContents.toString().trim());
            }
        }
        return slideContents;
    }


    //helper method for extract images from pptx
    private static String processPictureShape(XSLFPictureShape pictureShape, int targetWidth, int targetHeight) throws IOException {
        XSLFPictureData pictureData = pictureShape.getPictureData();
        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(pictureData.getData()));
        BufferedImage resizedImage = resizeImage(originalImage, targetWidth, targetHeight);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String imageFormat = pictureData.getContentType().split("/")[1];
        ImageIO.write(resizedImage, imageFormat, baos);

        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        // Calculate the new dimensions while maintaining the aspect ratio
        double scaleFactor = Math.min((double) targetWidth / originalImage.getWidth(), (double) targetHeight / originalImage.getHeight());
        int newWidth = (int) (originalImage.getWidth() * scaleFactor);
        int newHeight = (int) (originalImage.getHeight() * scaleFactor);

        // Create a new BufferedImage with the new dimensions
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

        // Use getScaledInstance with the smooth scaling hint to improve the quality
        Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        // Draw the scaled image onto the new BufferedImage
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(scaledImage, 0, 0, newWidth, newHeight, null);
        g.dispose();

        return resizedImage;
    }




    public static List<List<List<String>>> extractTables(String filePath) throws IOException {
        List<List<List<String>>> tables = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             XMLSlideShow ppt = new XMLSlideShow(fis)) {

            for (XSLFSlide slide : ppt.getSlides()) {
                for (XSLFShape shape : slide.getShapes()) {
                    if (shape instanceof XSLFTable) {
                        XSLFTable table = (XSLFTable) shape;
                        List<List<String>> tableData = new ArrayList<>();

                        for (int rowIndex = 0; rowIndex < table.getNumberOfRows(); rowIndex++) {
                            List<String> rowData = new ArrayList<>();
                            for (int colIndex = 0; colIndex < table.getNumberOfColumns(); colIndex++) {
                                XSLFTableCell cell = table.getCell(rowIndex, colIndex);
                                rowData.add(cell.getText());
                            }
                            tableData.add(rowData);
                        }

                        tables.add(tableData);
                    }
                }
            }
        }

        return tables;
    }



    public static String formatMarkdownOutput(List<String> slideContents) {
        StringBuilder output = new StringBuilder();
        for (String slideContent : slideContents) {
            output.append(slideContent).append("\n\n");
        }
        return output.toString().trim();
    }
}
