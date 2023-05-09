package com.tomislav.pptxtomarkdown.menu;

import com.tomislav.pptxtomarkdown.helpers.ExportHelper;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.web.WebView;

public class ExportMenu {

    public static MenuItem createExportMenu(WebView htmlPreview) {
        Menu exportMenu = new Menu("Export");

        MenuItem pdfItem = new MenuItem("PDF");
        pdfItem.setOnAction(e -> {
            ExportHelper.html_to_pdf_serializeDocument(htmlPreview);
        });

        MenuItem htmlItem = new MenuItem("HTML");
        htmlItem.setOnAction(e -> {
            // Handle HTML export
        });

        MenuItem markdownItem = new MenuItem("MARKDOWN");
        markdownItem.setOnAction(e -> {
            // Handle Markdown export
        });

        exportMenu.getItems().addAll(pdfItem, htmlItem, markdownItem);

        return exportMenu;
    }

}
