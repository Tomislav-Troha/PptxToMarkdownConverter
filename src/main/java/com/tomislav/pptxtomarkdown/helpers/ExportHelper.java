package com.tomislav.pptxtomarkdown.helpers;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class ExportHelper {



    public static void html_to_pdf_serializeDocument(WebView htmlPreview){
        try {
            String serializedDocument = (String) htmlPreview.getEngine().executeScript("document.documentElement.outerHTML");
            String escapedDocument = ExportHelper.escapeHtml(serializedDocument);
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File outputFile = fileChooser.showSaveDialog(htmlPreview.getScene().getWindow());
            if (outputFile != null) {
                exportHtmlToPdf(escapedDocument, outputFile.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void exportHtmlToPdf(String htmlContent, String outputFile) throws Exception {
        try (OutputStream os = new FileOutputStream(new File(outputFile))) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(htmlContent, null);
            builder.toStream(os);
            builder.run();
        }
    }

    private static String escapeHtml(String input) {
        Document document = Jsoup.parse(input, "", Parser.xmlParser());
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        document.outputSettings().escapeMode(org.jsoup.nodes.Entities.EscapeMode.xhtml);
        return document.html();
    }


}
