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

    public static VBox createLayout(Label label, Node outputArea) {
        return new VBox(10, label, outputArea);
    }
}
