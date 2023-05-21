package com.tomislav.pptxtomarkdown.menu;

import com.tomislav.pptxtomarkdown.helpers.MainViewHelper;
import com.tomislav.pptxtomarkdown.view.PptxToMarkDownView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCombination;

import java.util.ArrayList;
import java.util.List;

public class EditFileMenu {

    public static List<MenuItem> createEditMenu(PptxToMarkDownView view) {

        List<MenuItem> menuItems = new ArrayList<>();

        MenuItem undoItem = new MenuItem("Undo");
        undoItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Z"));
        undoItem.setOnAction(event -> view.getMarkdownOutput().undo());
        menuItems.add(undoItem);

        MenuItem redoItem = new MenuItem("Redo");
        redoItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Y"));
        redoItem.setOnAction(event -> view.getMarkdownOutput().redo());
        menuItems.add(redoItem);

        MenuItem saveItem = new MenuItem("Save");
        saveItem.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        saveItem.setOnAction(event -> MainViewHelper.saveFileListeners(view));
        menuItems.add(saveItem);

        SeparatorMenuItem separator = new SeparatorMenuItem();
        menuItems.add(separator);

        MenuItem cutItem = new MenuItem("Cut");
        cutItem.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
        cutItem.setOnAction(event -> view.getMarkdownOutput().cut());
        menuItems.add(cutItem);

        MenuItem copyItem = new MenuItem("Copy");
        copyItem.setAccelerator(KeyCombination.keyCombination("Ctrl+C"));
        copyItem.setOnAction(event -> view.getMarkdownOutput().copy());
        menuItems.add(copyItem);

        MenuItem pasteItem = new MenuItem("Paste");
        pasteItem.setAccelerator(KeyCombination.keyCombination("Ctrl+V"));
        pasteItem.setOnAction(event -> view.getMarkdownOutput().paste());
        menuItems.add(pasteItem);


        return menuItems;
    }

}
