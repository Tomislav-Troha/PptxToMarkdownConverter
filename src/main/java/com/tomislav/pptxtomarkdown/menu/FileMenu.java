package com.tomislav.pptxtomarkdown.menu;

import com.tomislav.pptxtomarkdown.helpers.ExportHelper;
import com.tomislav.pptxtomarkdown.helpers.MainViewHelper;
import com.tomislav.pptxtomarkdown.helpers.MenuCreatorHelper;
import com.tomislav.pptxtomarkdown.model.PptxMetadata;
import com.tomislav.pptxtomarkdown.utils.MarkdownGenerator;
import com.tomislav.pptxtomarkdown.utils.NotificationManager;
import com.tomislav.pptxtomarkdown.utils.PptxExtractor;
import com.tomislav.pptxtomarkdown.view.PptxToMarkDownView;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.*;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

public class FileMenu extends MarkdownGenerator {

    public static MenuItem ChooseMenuHandler(PptxToMarkDownView view) {
        final PptxExtractor extractor = new PptxExtractor();
        final MarkdownGenerator generator = new MarkdownGenerator();

        Menu menu = new Menu("Open");

        MenuItem chooseFile_MenuItem = new MenuItem();
        MenuItem chooseMarkdownFile_MenuItem = new MenuItem();

        // Choose file menu item (pptx)
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PowerPoint Files", "*.pptx"));
            // Choose file menu item
            chooseFile_MenuItem = MenuCreatorHelper.createChooseFileMenuItem("Choose pptx file", (v) -> {
                File selectedFile = fileChooser.showOpenDialog(new Stage());
                try {
                    if (selectedFile != null) {
                        view.setTitle(selectedFile.getName());
                        ProgressBar progressBar = view.getProgressBar();
                        progressBar.setVisible(true);

                        Task<Void> task = new Task<>() {
                            @Override
                            protected Void call() {
                                PptxMetadata metadata = extractor.extractMetadata(selectedFile.getAbsolutePath());
                                String markdown = generator.generateMarkdown(metadata);

                                Platform.runLater(() -> {
                                    view.getMarkdownOutput().setText(markdown);
                                    view.updateHtmlPreview(markdown);
                                });
                                return null;
                            }

                            @Override
                            protected void succeeded() {
                                super.succeeded();
                                NotificationManager.showMessageBox("File upload completed successfully", Alert.AlertType.INFORMATION, Duration.seconds(3));
                                view.setSaveLocation(null);
                                view.getPptxFileLoaded().set(true);
                                progressBar.setVisible(false);
                            }

                            @Override
                            protected void failed() {
                                super.failed();
                                try {
                                    progressBar.setVisible(false);
                                    throw new Exception("Error while uploading file!");
                                } catch (Exception e) {
                                    NotificationManager.showMessageBox(e.getMessage(), Alert.AlertType.ERROR, Duration.seconds(3));
                                }
                            }
                        };
                        new Thread(task).start();
                    }
                } catch (Exception e) {
                    NotificationManager.showMessageBox(e + " " + e.getMessage(), Alert.AlertType.ERROR, Duration.seconds(3));
                }
            });
        } catch (Exception e) {
            NotificationManager.showMessageBox(e + " " + e.getMessage(), Alert.AlertType.ERROR, Duration.seconds(3));
        }

        // Choose file menu item (markdown)
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Markdown Files", "*.md"));
            chooseMarkdownFile_MenuItem = MenuCreatorHelper.createChooseFileMenuItem("Choose markdown file", (v) -> {
                File selectedFile = fileChooser.showOpenDialog(new Stage());
                try {
                    if (selectedFile != null) {
                        view.setTitle(selectedFile.getName());
                        ProgressBar progressBar = view.getProgressBar();
                        progressBar.setVisible(true);

                        Task<Void> task = new Task<>() {
                            @Override
                            protected Void call() throws IOException {
                                // Read Markdown text from file
                                String markdown = Files.readString(selectedFile.toPath());

                                Platform.runLater(() -> {
                                    view.getMarkdownOutput().setText(markdown);
                                    view.updateHtmlPreview(markdown);
                                });
                                return null;
                            }

                            @Override
                            protected void succeeded() {
                                super.succeeded();
                                NotificationManager.showMessageBox("File upload completed successfully", Alert.AlertType.INFORMATION, Duration.seconds(3));
                                view.getMarkdownFileLoaded().set(true);
                                view.setSaveLocation(selectedFile);
                                progressBar.setVisible(false);
                            }

                            @Override
                            protected void failed() {
                                super.failed();
                                NotificationManager.showMessageBox("Error while uploading file!", Alert.AlertType.ERROR, Duration.seconds(3));
                                progressBar.setVisible(false);
                            }
                        };
                        new Thread(task).start();
                    }
                } catch (Exception e) {
                    NotificationManager.showMessageBox(e + " " + e.getMessage(), Alert.AlertType.ERROR, Duration.seconds(3));
                }
            });
        } catch (Exception e) {
            NotificationManager.showMessageBox(e + " " + e.getMessage(), Alert.AlertType.ERROR, Duration.seconds(3));
        }

        menu.getItems().addAll(chooseFile_MenuItem, chooseMarkdownFile_MenuItem);

        return menu;
    }

    public static MenuItem createExportMenu(WebView htmlPreview) {
        Menu exportMenu = new Menu("Export");

        MenuItem pdfItem = new MenuItem("PDF");
        pdfItem.setOnAction(e -> {
            try{
                ExportHelper.html_to_pdf_serializeDocument(htmlPreview);
            }
            catch (Exception ex){
                NotificationManager.showMessageBox(ex.getMessage(), Alert.AlertType.ERROR, Duration.seconds(3));
            }

        });
        exportMenu.getItems().addAll(pdfItem);

        return exportMenu;
    }

    public static MenuItem createNewFile(PptxToMarkDownView view){
        MenuItem newFile = new MenuItem("New");
        newFile.setOnAction(e -> {
            // Check if the file has been modified
            if (view.getModified().get()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Save Changes");
                alert.setHeaderText(null);
                alert.setContentText("Do you want to save changes you made?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    MainViewHelper.saveFileListeners(view);
                    if(view.getSaveLocation() != null){
                        clearMarkdownEditor(view);
                    }
                }
            }
            clearMarkdownEditor(view);
        });
        return newFile;
    }

    private static void clearMarkdownEditor(PptxToMarkDownView view){
            // Reset the markdown editor
            view.getMarkdownOutput().clear();

            // Reset the save location
            view.setSaveLocation(null);

            // Reset the modified flag
            view.getModified().set(false);

            // Reset the file loaded flags
            view.getMarkdownFileLoaded().set(false);
            view.getPptxFileLoaded().set(false);

            view.getMarkdownLabel().setText("Markdown ");
            view.setTitle(null);
    }
}
