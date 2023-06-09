package com.tomislav.pptxtomarkdown.utils;

import com.tomislav.pptxtomarkdown.App;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.*;
import javafx.util.Duration;

    public class NotificationManager {

        public static void showMessageBox(String message, Alert.AlertType alertType, Duration duration) {

            Stage stage = new Stage();
            stage.initModality(Modality.NONE);
            stage.initStyle(StageStyle.TRANSPARENT);

            stage.initOwner(App.mainStage);

            Stage mainStage = App.mainStage;

            ChangeListener<Number> yListener = (observable, oldValue, newValue) -> {
                double newY = mainStage.getY() + mainStage.getHeight() / 2 - stage.getHeight() / 2;
                stage.setY(newY);
            };
            ChangeListener<Number> xListener = (observable, oldValue, newValue) -> {
                double newX = mainStage.getX() + mainStage.getWidth() / 2 - stage.getWidth() / 2;
                stage.setX(newX);
            };

            mainStage.getScene().getWindow().yProperty().addListener(yListener);
            mainStage.getScene().getWindow().xProperty().addListener(xListener);

            Label label = new Label(message);
            label.setWrapText(true); // Wrap the text
            label.setMaxWidth(300); // Set the maximum width for the label
            label.setStyle("-fx-font-size: 16px; -fx-padding: 10px; -fx-background-radius: 10px;");

            // Set the background color and text color based on the alert type
            if (alertType == Alert.AlertType.ERROR) {
                label.setStyle(label.getStyle() + "-fx-text-fill: white;");
                label.setStyle(label.getStyle() + "-fx-background-color: red;");
            } else if (alertType == Alert.AlertType.INFORMATION) {
                label.setStyle(label.getStyle() + "-fx-text-fill: white;");
                label.setStyle(label.getStyle() + "-fx-background-color: green;");
            }

            VBox root = new VBox(label);
            root.setAlignment(Pos.CENTER);
            Scene scene = new Scene(root);
            scene.setFill(null);
            stage.setScene(scene);

            // Set the position and make it always on top
            stage.setX(getScreenWidth() - root.getWidth() - 300);
            stage.setY(getScreenHeight() - root.getHeight() - 100);
            stage.setAlwaysOnTop(true);

            // Slide up and down animation
            TranslateTransition slideUp = new TranslateTransition(Duration.millis(500), root);
            slideUp.setFromY(50);
            slideUp.setToY(0);

            TranslateTransition slideDown = new TranslateTransition(Duration.millis(500), root);
            slideDown.setFromY(0);
            slideDown.setToY(50);


            // Play the animations in sequence
            SequentialTransition sequentialTransition = new SequentialTransition(slideUp, new PauseTransition(duration), slideDown);
            sequentialTransition.setOnFinished(e -> stage.close());
            sequentialTransition.play();

            stage.show();
        }

        private static double getScreenWidth() {
            return App.mainStage.getWidth();
        }

        private static double getScreenHeight() {
            return App.mainStage.getHeight();
        }
    }

