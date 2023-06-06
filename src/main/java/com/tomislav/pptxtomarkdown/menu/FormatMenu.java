package com.tomislav.pptxtomarkdown.menu;

import com.tomislav.pptxtomarkdown.helpers.FormatMenuHelper;
import com.tomislav.pptxtomarkdown.utils.NotificationManager;
import com.tomislav.pptxtomarkdown.view.PptxToMarkDownView;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class FormatMenu {

    public static List<MenuItem> createFormatMenu(PptxToMarkDownView view) {
        List<MenuItem> menus = new ArrayList<>();

        MenuItem strongItem = new MenuItem("Strong");
        strongItem.setOnAction(e -> FormatMenuHelper.selectText(view, "**", "**", "\\**"));
        menus.add(strongItem);

        MenuItem emphasisItem = new MenuItem("Emphasis");
        emphasisItem.setOnAction(e -> FormatMenuHelper.selectText(view, "*", "*", "\\*"));
        menus.add(emphasisItem);

        MenuItem underlineItem = new MenuItem("Underline");
        underlineItem.setOnAction(e -> FormatMenuHelper.selectText(view, "<u>", "</u>"));
        menus.add(underlineItem);

        menus.add(new SeparatorMenuItem());

        MenuItem strikeItem = new MenuItem("Strike");
        strikeItem.setOnAction(e -> FormatMenuHelper.selectText(view, "<s>", "</s>"));
        menus.add(strikeItem);

        MenuItem highlightItem = new MenuItem("Highlight");
        highlightItem.setOnAction(e -> FormatMenuHelper.selectText(view, "<span style=\"background-color: #FFFF00\">", "</span>"));
        menus.add(highlightItem);

        MenuItem superScriptItem = new MenuItem("SuperScript");
        superScriptItem.setOnAction(e -> FormatMenuHelper.selectText(view, "<sup>", "</sup>"));
        menus.add(superScriptItem);

        menus.add(new SeparatorMenuItem());

        MenuItem alignLeftItem = new MenuItem("Align Left");
        alignLeftItem.setOnAction(e -> FormatMenuHelper.selectText(view, "<p style=\"text-align: left;\">", "</p>"));
        menus.add(alignLeftItem);

        MenuItem alignRightItem = new MenuItem("Align Right");
        alignRightItem.setOnAction(e -> FormatMenuHelper.selectText(view, "<p style=\"text-align: right;\">", "</p>"));
        menus.add(alignRightItem);

        MenuItem alignCenterItem = new MenuItem("Align Center");
        alignCenterItem.setOnAction(e -> FormatMenuHelper.selectText(view, "<p style=\"text-align: center;\">", "</p>"));
        menus.add(alignCenterItem);

        menus.add(new SeparatorMenuItem());

        Menu imageMenu = new Menu("Image");

        MenuItem insertLocalImageItem = new MenuItem("Insert Local Image");
        insertLocalImageItem.setOnAction(e -> FormatMenuHelper.insertLocalImage(view));

        imageMenu.getItems().addAll(insertLocalImageItem);
        menus.add(imageMenu);

        menus.add(new SeparatorMenuItem());

        MenuItem clearItem = new MenuItem("Clear Formatting");
        clearItem.setOnAction(e -> FormatMenuHelper.selectText(view, "", "", "clear"));
        menus.add(clearItem);

        return menus;
    }

}
