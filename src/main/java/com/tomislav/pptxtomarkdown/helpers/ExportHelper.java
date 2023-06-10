package com.tomislav.pptxtomarkdown.helpers;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.sun.tools.javac.Main;
import com.tomislav.pptxtomarkdown.App;
import com.tomislav.pptxtomarkdown.css.Fonts;
import com.tomislav.pptxtomarkdown.utils.NotificationManager;
import javafx.concurrent.Worker;
import javafx.scene.control.Alert;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class ExportHelper {



    public static void html_to_pdf_serializeDocument(WebView htmlPreview){
        try {
            WebEngine webEngine = htmlPreview.getEngine();
            if (webEngine.getLoadWorker().getState() == Worker.State.SUCCEEDED) {
                serializeAndExportHtml(webEngine, htmlPreview);
            } else {
                webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue == Worker.State.SUCCEEDED) {
                        serializeAndExportHtml(webEngine, htmlPreview);
                    }
                });
            }
        } catch (Exception e) {
            NotificationManager.showMessageBox(e.getMessage(), Alert.AlertType.ERROR, Duration.seconds(2));
        }
    }

    private static void serializeAndExportHtml(WebEngine webEngine, WebView htmlPreview) {
        String serializedDocument = (String) webEngine.executeScript("document.documentElement.outerHTML");
        String escapedDocument = ExportHelper.escapeHtml(serializedDocument);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File outputFile = fileChooser.showSaveDialog(htmlPreview.getScene().getWindow());
        if (outputFile != null) {
            try {
                exportHtmlToPdf(escapedDocument, outputFile.getAbsolutePath());
                NotificationManager.showMessageBox("PDF successfully imported", Alert.AlertType.INFORMATION, Duration.seconds(2));
            } catch (Exception e) {
                NotificationManager.showMessageBox(e.getMessage(), Alert.AlertType.ERROR, Duration.seconds(2));
            }
        }
    }

    private static void exportHtmlToPdf(String htmlContent, String outputFile) throws Exception {
        try (OutputStream os = new FileOutputStream(outputFile)) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.useFont(new File(Main.class.getClassLoader().getResource("export_fonts/Arial_Unicode_MS.ttf").getFile()), "Arial Unicode MS");
            builder.withW3cDocument(new W3CDom().fromJsoup(Jsoup.parse(htmlContent)), null);
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
