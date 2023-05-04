package com.tomislav.pptxtomarkdown.menu;

import com.tomislav.pptxtomarkdown.helpers.MenuCreatorHelper;
import com.tomislav.pptxtomarkdown.model.PptxMetadata;
import com.tomislav.pptxtomarkdown.utils.MarkdownGenerator;
import com.tomislav.pptxtomarkdown.utils.NotificationManager;
import com.tomislav.pptxtomarkdown.utils.PptxExtractor;
import com.tomislav.pptxtomarkdown.view.PptxToMarkDownView;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class ChooseFileMenu {

    public static MenuItem ChooseMenuHandler(PptxToMarkDownView view) {
        final PptxExtractor extractor = new PptxExtractor();
        final MarkdownGenerator generator = new MarkdownGenerator();
        MenuItem chooseFile_MenuItem = new MenuItem();

        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PowerPoint Files", "*.pptx"));
            // Choose file menu item
            //PptxMetadata metadata = extractor.extractMetadata("C:\\Users\\tomis\\Desktop\\Upravljanje-znanjem-u-obrazovanju.pptx");
            chooseFile_MenuItem = MenuCreatorHelper.createChooseFileMenuItem("Choose pptx file", (v) -> {
                File selectedFile = fileChooser.showOpenDialog(new Stage());
                try {
                    if (selectedFile != null) {
                        ProgressBar progressBar = view.getProgressBar();
                        progressBar.setVisible(true);

                        Task<Void> task = new Task<>() {
                            @Override
                            protected Void call() {
                                PptxMetadata metadata = extractor.extractMetadata(selectedFile.getAbsolutePath());
                                //PptxMetadata metadata = extractor.extractMetadata("C:\\Users\\tomis\\Desktop\\Upravljanje-znanjem-u-obrazovanju.pptx");
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
                                NotificationManager.showMessageBox("Success ", "File upload completed successfully", Alert.AlertType.INFORMATION, Duration.seconds(3));
                                progressBar.setVisible(false);
                            }

                            @Override
                            protected void failed() {
                                super.failed();
                                NotificationManager.showMessageBox("Error", "Error while uploading file!", Alert.AlertType.ERROR, Duration.seconds(3));
                                progressBar.setVisible(false);
                            }
                        };
                        new Thread(task).start();
                    }
                } catch (Exception e) {
                    NotificationManager.showMessageBox("Error ", e + " " + e.getMessage(), Alert.AlertType.ERROR, Duration.seconds(3));
                }
            });
        } catch (Exception e) {
            NotificationManager.showMessageBox("Error ", e + " " + e.getMessage(), Alert.AlertType.ERROR, Duration.seconds(3));
        }
        return chooseFile_MenuItem;
    }

}
