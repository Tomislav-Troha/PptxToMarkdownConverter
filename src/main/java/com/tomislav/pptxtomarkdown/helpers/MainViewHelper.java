package com.tomislav.pptxtomarkdown.helpers;

import com.tomislav.pptxtomarkdown.model.PptxMetadata;
import com.tomislav.pptxtomarkdown.utils.MarkdownGenerator;
import com.tomislav.pptxtomarkdown.utils.PptxExtractor;
import com.tomislav.pptxtomarkdown.view.PptxToMarkDownView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainViewHelper {

    public static Button createExportButton(String buttonText, String exportPdfText, String exportWordText, EventHandler<ActionEvent> pdfAction) {
        Button exportButton = new Button(buttonText);
        MenuItem exportToPdf = new MenuItem(exportPdfText);
        MenuItem exportToWord = new MenuItem(exportWordText);

        // Set the action event handlers for the menu items
        exportToPdf.setOnAction(pdfAction);
//        exportToWord.setOnAction(wordAction);

        ContextMenu exportMenu = new ContextMenu();
        exportMenu.getItems().addAll(exportToPdf, exportToWord);

        exportButton.setOnMouseClicked(event -> {
            exportMenu.show(exportButton, event.getScreenX(), event.getScreenY());
        });

        return exportButton;
    }


    private static HBox createExportButtonContainer(Button exportButton) {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox exportButtonContainer = new HBox();
        exportButtonContainer.getChildren().addAll(spacer, exportButton);
        return exportButtonContainer;
    }

    public static VBox createLayout(String labelText, Node outputArea, Button exportButton) {
        Label label = new Label(labelText);
        HBox exportButtonContainer = createExportButtonContainer(exportButton);
        VBox layout = new VBox(10, label, outputArea, exportButtonContainer);
        return layout;
    }
}
