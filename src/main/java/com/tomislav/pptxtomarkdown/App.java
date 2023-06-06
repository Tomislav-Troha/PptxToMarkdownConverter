package com.tomislav.pptxtomarkdown;
import com.tomislav.pptxtomarkdown.controller.PptxToMarkdownController;
import com.tomislav.pptxtomarkdown.view.PptxToMarkDownView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class App extends Application {

    public static Stage mainStage;
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;
        PptxToMarkDownView view = new PptxToMarkDownView();
        PptxToMarkdownController controller = new PptxToMarkdownController(view);

        mainStage.getIcons().add(new Image("/pptx-to-markdown-converter-logo.png"));

        Scene scene = view.createScene(view);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        primaryStage.setTitle("Pptx to Markdown Converter");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}
