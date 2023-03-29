package com.tomislav.pptxtomarkdown;
import com.tomislav.pptxtomarkdown.controller.PptxToMarkdownController;
import com.tomislav.pptxtomarkdown.view.PptxToMarkDownView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        PptxToMarkDownView view = new PptxToMarkDownView();
        PptxToMarkdownController controller = new PptxToMarkdownController(view);

        Scene scene = view.createScene();

        primaryStage.setTitle("Pptx to Markdown");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
