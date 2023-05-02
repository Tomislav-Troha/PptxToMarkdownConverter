package com.tomislav.pptxtomarkdown.view;

import com.tomislav.pptxtomarkdown.css.Fonts;
import com.tomislav.pptxtomarkdown.helpers.ExportHelper;
import com.tomislav.pptxtomarkdown.helpers.MainViewHelper;
import com.tomislav.pptxtomarkdown.helpers.MenuCreatorHelper;
import com.tomislav.pptxtomarkdown.utils.MarkdownToHtmlConverter;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;

public class PptxToMarkDownView {

    private final FileChooser fileChooser = new FileChooser();
//    private final MenuButton fontMenuButton;
    private final TextArea markdownOutput;
    private final WebView htmlPreview;
    private final Button exportHtmlButton;
    private final Button exportMarkdownButton;
    private ProgressBar progressBar;

    MarkdownToHtmlConverter htmlConverter = new MarkdownToHtmlConverter();

    public PptxToMarkDownView() {

//        chooseFileButton = new Button("Odaberi pptx datoteku");
//        filePathTextField = new TextField();
//        filePathTextField.setEditable(false);
//        filePathTextField.setPromptText("Odabrana datoteka");

        markdownOutput = new TextArea();
        markdownOutput.setPrefSize(794, 1123);
        markdownOutput.setPromptText("Markdown izlaz");
        markdownOutput.setEditable(true);

        //loop through fonts enum and add menu items
//        fontMenuButton = new MenuButton("Odaberi font");
//        for(Fonts font : Fonts.values()){
//            MenuItem menuItem = new MenuItem(font.getFontName());
//            fontMenuButton.getItems().add(menuItem);
//        }
//
//        //set action for menu items
//        fontMenuButton.getItems().forEach(item -> {
//            item.setOnAction(event -> {
//                String fontName = item.getText();
//                fontMenuButton.setText(fontName);
//                updateHtmlPreview(markdownOutput.getText(), fontName);
//            });
//        });

        //export markdown to pdf or word
        exportMarkdownButton = MainViewHelper.createExportButton("Izvezi", "Izvezi markdown u PDF", "Izvezi markdown u Word", event -> {});

        //update html preview when text in markdownOutput changes
        markdownOutput.textProperty().addListener((observable, oldValue, newValue) -> updateHtmlPreview(newValue));

        htmlPreview = new WebView();
        htmlPreview.setPrefSize(794, 1123);

        //export html preview to pdf
        exportHtmlButton = MainViewHelper.createExportButton("Izvezi", "Izvezi HTML u PDF", "Izvezi HTML u Word", event -> {
            ExportHelper.html_to_pdf_serializeDocument(htmlPreview);
        });

    }

    public Scene createScene(PptxToMarkDownView view) {
        MenuCreatorHelper menuCreatorHelper = new MenuCreatorHelper();
        MenuBar menuBar = menuCreatorHelper.createMenuBar(view);

        HBox fileInputLayout = new HBox(10);
        VBox markdownLayout = MainViewHelper.createLayout("Markdown izlaz:", markdownOutput, exportMarkdownButton);
        VBox htmlLayout = MainViewHelper.createLayout("HTML pregled:", htmlPreview, exportHtmlButton);

        SplitPane splitPane = new SplitPane(markdownLayout, htmlLayout);
        splitPane.setDividerPositions(0.5);

        VBox topLayout = new VBox(menuBar, fileInputLayout); // Combine the menuBar and fileInputLayout

        BorderPane root = new BorderPane();
        root.setTop(topLayout);
        root.setCenter(splitPane);
        root.setBottom(getProgressBar());

        return new Scene(root);
    }


    //function for updating html preview
    public void updateHtmlPreview(String markdown, String... fontName) {
        WebEngine webEngine = htmlPreview.getEngine();
        String cssFileUrl = getClass().getResource("/markdown-preview.css").toExternalForm();
        webEngine.setUserStyleSheetLocation(cssFileUrl);
        String html = htmlConverter.convertMarkdownToHtml(markdown, fontName.length >= 1 ? fontName[0]: Fonts.ARIAL.getFontName());
        webEngine.loadContent(html);
    }

    // Getters for all view elements
    public TextArea getMarkdownOutput() {
        return markdownOutput;
    }

    public FileChooser getFileChooser() {
        return fileChooser;
    }
    public ProgressBar getProgressBar() {
        if (progressBar == null) {
            progressBar = new ProgressBar();
            progressBar.setMaxWidth(Double.MAX_VALUE);
            progressBar.setVisible(false);
        }
        return progressBar;
    }
}
