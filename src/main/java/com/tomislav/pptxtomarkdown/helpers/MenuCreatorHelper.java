package com.tomislav.pptxtomarkdown.helpers;

import com.tomislav.pptxtomarkdown.model.PptxMetadata;
import com.tomislav.pptxtomarkdown.utils.MarkdownGenerator;
import com.tomislav.pptxtomarkdown.utils.PptxExtractor;
import com.tomislav.pptxtomarkdown.view.PptxToMarkDownView;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public class MenuCreatorHelper {

    private FileChooser fileChooser;

        public static MenuItem createChooseFileMenuItem(String text, Consumer<Void> action) {
            MenuItem chooseFileMenuItem = new MenuItem(text);
            chooseFileMenuItem.setOnAction(event -> action.accept(null));
            return chooseFileMenuItem;
        }

        public static CustomMenuItem createFilePathMenuItem(String promptText) {
            TextField filePathTextField = new TextField();
            filePathTextField.setEditable(false);
            filePathTextField.setPromptText(promptText);
            CustomMenuItem filePathMenuItem = new CustomMenuItem(filePathTextField);
            filePathMenuItem.setHideOnClick(false);
            return filePathMenuItem;
        }

    public MenuBar createMenuBar(PptxToMarkDownView view) {

        final PptxExtractor extractor = new PptxExtractor();
        final MarkdownGenerator generator = new MarkdownGenerator();

        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PowerPoint Files", "*.pptx, *.ppt"));
        // Choose file menu item
        MenuItem chooseFile_MenuItem = MenuCreatorHelper.createChooseFileMenuItem("Odaberi datoteku", (v) -> {
            File selectedFile = view.getFileChooser().showOpenDialog(new Stage());
                if (selectedFile != null) {
                    ProgressBar progressBar = view.getProgressBar();
                    progressBar.setVisible(true);

                    Task<Void> task = new Task<Void>(){
                        @Override
                        protected Void call() throws Exception {
                            try {
                                PptxMetadata metadata = extractor.extractMetadata(selectedFile.getAbsolutePath());
                                //PptxMetadata metadata = extractor.extractMetadata("C:\\Users\\tomis\\Desktop\\Upravljanje-znanjem-u-obrazovanju.pptx");
                                String markdown = generator.generateMarkdown(metadata);

                                Platform.runLater(() -> {
                                    view.getMarkdownOutput().setText(markdown);
                                    view.updateHtmlPreview(markdown);
                                });

                            } catch (IOException e) {
                                view.getMarkdownOutput().setText("Pogreška prilikom citanja pptx datoteke: " + e.getMessage());
                            }
                            return null;
                        }
                        @Override
                        protected void succeeded() {
                            super.succeeded();
                            progressBar.setVisible(false);
                        }
                        @Override
                        protected void failed() {
                            super.failed();
                            progressBar.setVisible(false);
                        }
                    };
                    new Thread(task).start();
                }
        });

        // File path text field menu item
        // CustomMenuItem filePathMenuItem = MenuCreatorHelper.createFilePathMenuItem("Odabrana datoteka");

        // Create the File menu and add the custom menu items
        Menu fileMenu = new Menu("File");
        fileMenu.getItems().addAll(chooseFile_MenuItem);

        Menu editMenu = new Menu("Edit");
        Menu paragraphMenu = new Menu("Paragraph");
        Menu formatMenu = new Menu("Format");
        Menu viewMenu = new Menu("View");
        Menu themesMenu = new Menu("Themes");
        Menu helpMenu = new Menu("Help");

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, editMenu, paragraphMenu, formatMenu, viewMenu, themesMenu, helpMenu);

        return menuBar;
    }
}
