package com.tomislav.pptxtomarkdown.helpers;

import com.tomislav.pptxtomarkdown.menu.ChooseFileMenu;
import com.tomislav.pptxtomarkdown.menu.EditFileMenu;
import com.tomislav.pptxtomarkdown.menu.ExportMenu;
import com.tomislav.pptxtomarkdown.menu.ParagraphMenu;
import com.tomislav.pptxtomarkdown.view.PptxToMarkDownView;
import javafx.scene.control.*;

import java.util.function.Consumer;

public class MenuCreatorHelper {

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

        // CustomMenuItem filePathMenuItem = MenuCreatorHelper.createFilePathMenuItem("Odabrana datoteka");

        Menu fileMenu = new Menu("File");
        fileMenu.getItems().addAll(ChooseFileMenu.ChooseMenuHandler(view), new SeparatorMenuItem(), ExportMenu.createExportMenu(view.getHtmlPreview()));

        Menu editMenu = new Menu("Edit");
        editMenu.getItems().addAll(EditFileMenu.createEditMenu(view));

        Menu paragraphMenu = new Menu("Paragraph");
        paragraphMenu.getItems().addAll(ParagraphMenu.createParagraphMenuItem(view));

        Menu formatMenu = new Menu("Format");
        Menu viewMenu = new Menu("View");
        Menu themesMenu = new Menu("Themes");
        Menu helpMenu = new Menu("Help");

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, editMenu, paragraphMenu, formatMenu, viewMenu, themesMenu, helpMenu);

        return menuBar;
    }
}
