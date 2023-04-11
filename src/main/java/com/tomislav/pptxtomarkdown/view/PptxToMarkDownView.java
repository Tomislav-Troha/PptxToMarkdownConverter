package com.tomislav.pptxtomarkdown.view;

import com.tomislav.pptxtomarkdown.css.Fonts;
import com.tomislav.pptxtomarkdown.css.LatexPattern;
import com.tomislav.pptxtomarkdown.utils.ExportHelper;
import com.tomislav.pptxtomarkdown.utils.MainViewHelper;
import com.tomislav.pptxtomarkdown.utils.MarkdownToHtmlConverter;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import java.io.File;

import static com.tomislav.pptxtomarkdown.utils.ExportHelper.exportHtmlToPdf;

public class PptxToMarkDownView {

    private final FileChooser fileChooser;
    private final Button chooseFileButton;
    private final TextField filePathTextField;
    private final MenuButton fontMenuButton;
    private final TextArea markdownOutput;
    private final WebView htmlPreview;
    private final Button exportHtmlButton;
    private final Button exportMarkdownButton;

    public PptxToMarkDownView() {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PowerPoint Files", "*.pptx"));

        chooseFileButton = new Button("Odaberi pptx datoteku");
        filePathTextField = new TextField();
        filePathTextField.setEditable(false);
        filePathTextField.setPromptText("Odabrana datoteka");

        markdownOutput = new TextArea();
        markdownOutput.setPrefSize(794, 1123);
        markdownOutput.setPromptText("Markdown izlaz");
        markdownOutput.setEditable(true);

        //loop through fonts enum and add menu items
        fontMenuButton = new MenuButton("Odaberi font");
        for(Fonts font : Fonts.values()){
            MenuItem menuItem = new MenuItem(font.getFontName());
            fontMenuButton.getItems().add(menuItem);
        }

        //set action for menu items
        fontMenuButton.getItems().forEach(item -> {
            item.setOnAction(event -> {
                String fontName = item.getText();
                fontMenuButton.setText(fontName);
                updateHtmlPreview(markdownOutput.getText(), fontName);
            });
        });

        //export markdown to pdf or word
        exportMarkdownButton = MainViewHelper.createExportButton("Izvezi", "Izvezi markdown u PDF", "Izvezi markdown u Word", event -> {});

        //update html preview when text in markdownOutput changes
        markdownOutput.textProperty().addListener((observable, oldValue, newValue) -> updateHtmlPreview(newValue));

        htmlPreview = new WebView();
        htmlPreview.setPrefSize(794, 1123);

        //export html preview to pdf or word
        exportHtmlButton = MainViewHelper.createExportButton("Izvezi", "Izvezi HTML u PDF", "Izvezi HTML u Word", event -> {
            try {
                String serializedDocument = (String) htmlPreview.getEngine().executeScript("document.documentElement.outerHTML");
                String escapedDocument = ExportHelper.escapeHtml(serializedDocument);
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save PDF");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
                File outputFile = fileChooser.showSaveDialog(htmlPreview.getScene().getWindow());
                if (outputFile != null) {
                    ExportHelper.exportHtmlToPdf(escapedDocument, outputFile.getAbsolutePath());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    public Scene createScene() {
        HBox fileInputLayout = new HBox(10, chooseFileButton, filePathTextField, fontMenuButton);
        VBox markdownLayout = MainViewHelper.createLayout("Markdown izlaz:", markdownOutput, exportMarkdownButton);
        VBox htmlLayout = MainViewHelper.createLayout("HTML pregled:", htmlPreview, exportHtmlButton);

        SplitPane splitPane = new SplitPane(markdownLayout, htmlLayout);
        splitPane.setDividerPositions(0.5);

        BorderPane root = new BorderPane();
        root.setTop(fileInputLayout);
        root.setCenter(splitPane);

        return new Scene(root);
    }


    //function for updating html preview
    public void updateHtmlPreview(String markdown, String... fontName) {
        WebEngine webEngine = htmlPreview.getEngine();
        String html = MarkdownToHtmlConverter.convertMarkdownToHtml(markdown, fontName.length >= 1 ? fontName[0]: Fonts.ARIAL.getFontName());
        webEngine.loadContent(html);
    }

    // Getters for all view elements
    public WebView getHtmlPreview() {
        return htmlPreview;
    }

    public FileChooser getFileChooser() {
        return fileChooser;
    }

    public Button getChooseFileButton() {
        return chooseFileButton;
    }

    public TextField getFilePathTextField() {
        return filePathTextField;
    }

    public TextArea getMarkdownOutput() {
        return markdownOutput;
    }

}
