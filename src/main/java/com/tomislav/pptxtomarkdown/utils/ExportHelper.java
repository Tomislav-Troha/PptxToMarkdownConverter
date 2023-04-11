package com.tomislav.pptxtomarkdown.utils;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class ExportHelper {

    public static void exportHtmlToPdf(String htmlContent, String outputFile) throws Exception {
        try (OutputStream os = new FileOutputStream(new File(outputFile))) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(htmlContent, null);
            builder.toStream(os);
            builder.run();
        }
    }

    public static String escapeHtml(String input) {
        Document document = Jsoup.parse(input, "", Parser.xmlParser());
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        document.outputSettings().escapeMode(org.jsoup.nodes.Entities.EscapeMode.xhtml);
        return document.html();
    }


}
