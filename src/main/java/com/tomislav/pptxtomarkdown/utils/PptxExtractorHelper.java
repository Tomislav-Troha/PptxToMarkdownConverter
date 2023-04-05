package com.tomislav.pptxtomarkdown.utils;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.sl.usermodel.TextShape;
import org.apache.poi.xslf.usermodel.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class PptxExtractorHelper {

    public static List<String> extractMetadatas(String filePath) throws IOException {
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
                            String text = textShape.getText().replaceAll("\n", "\n\n");
                            stringSlideContents.append("\n\n").append(text);
                        }
                    }

                    if (shape instanceof XSLFPictureShape) {
                        String base64Image = processPictureShape((XSLFPictureShape) shape);
                        stringSlideContents.append("\n\n").append("![image alt text](data:image/png;base64,").append(base64Image).append(")");
                    }
                }
                slideContents.add(stringSlideContents.toString().trim());
            }
        }
        return slideContents;
    }



    private static String processPictureShape(XSLFPictureShape pictureShape) throws IOException {
        XSLFPictureData pictureData = pictureShape.getPictureData();
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(pictureData.getData()));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String imageFormat = pictureData.getContentType().split("/")[1];
        ImageIO.write(image, imageFormat, baos);

        return Base64.getEncoder().encodeToString(baos.toByteArray());
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