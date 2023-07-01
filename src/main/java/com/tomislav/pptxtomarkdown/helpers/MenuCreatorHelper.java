package com.tomislav.pptxtomarkdown.helpers;

import com.tomislav.pptxtomarkdown.menu.FileMenu;
import com.tomislav.pptxtomarkdown.menu.EditFileMenu;
import com.tomislav.pptxtomarkdown.menu.FormatMenu;
import com.tomislav.pptxtomarkdown.menu.ParagraphMenu;
import com.tomislav.pptxtomarkdown.view.PptxToMarkDownView;
import javafx.scene.control.*;

import java.util.function.Consumer;

public class MenuCreatorHelper extends PptxToMarkDownView {

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

    public static MenuBar createMenuBar(PptxToMarkDownView view) {

        // CustomMenuItem filePathMenuItem = MenuCreatorHelper.createFilePathMenuItem("Odabrana datoteka");

        Menu fileMenu = new Menu("File");
        fileMenu.getItems().addAll
        (
                FileMenu.createNewFile(view),
                new SeparatorMenuItem(),
                FileMenu.ChooseMenuHandler(view),
                new SeparatorMenuItem(),
                FileMenu.createExportMenu(view.getHtmlPreview())
        );

        Menu editMenu = new Menu("Edit");
        editMenu.getItems().addAll(EditFileMenu.createEditMenu(view));

        Menu paragraphMenu = new Menu("Paragraph");
        paragraphMenu.getItems().addAll(ParagraphMenu.createParagraphMenuItem(view));

        Menu formatMenu = new Menu("Format");
        formatMenu.getItems().addAll(FormatMenu.createFormatMenu(view));

        Menu viewMenu = new Menu("View");
        Menu themesMenu = new Menu("Themes");
        Menu helpMenu = new Menu("Help");

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, editMenu, paragraphMenu, formatMenu, viewMenu, themesMenu, helpMenu);

        return menuBar;
    }
}
