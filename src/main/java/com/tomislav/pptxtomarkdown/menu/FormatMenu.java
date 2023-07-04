package com.tomislav.pptxtomarkdown.menu;

import com.tomislav.pptxtomarkdown.helpers.FormatMenuHelper;
import com.tomislav.pptxtomarkdown.view.PptxToMarkDownView;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

public class FormatMenu {

    public static List<MenuItem> createFormatMenu(PptxToMarkDownView view) {
        List<MenuItem> menus = new ArrayList<>();

        MenuItem strongItem = new MenuItem("Strong");
        strongItem.setOnAction(e -> FormatMenuHelper.selectText(view, "**", "**", strongItem, "\\**"));
        menus.add(strongItem);

        MenuItem emphasisItem = new MenuItem("Emphasis");
        emphasisItem.setOnAction(event -> {
            FormatMenuHelper.selectText(view, "*", "*", emphasisItem,"\\*");
        });
        menus.add(emphasisItem);

        MenuItem underlineItem = new MenuItem("Underline");
        underlineItem.setOnAction(e -> FormatMenuHelper.selectText(view, "<u>", "</u>", underlineItem));
        menus.add(underlineItem);

        menus.add(new SeparatorMenuItem());

        MenuItem strikeItem = new MenuItem("Strike");
        strikeItem.setOnAction(e -> FormatMenuHelper.selectText(view, "<s>", "</s>", strikeItem));
        menus.add(strikeItem);

        MenuItem highlightItem = new MenuItem("Highlight");
        highlightItem.setOnAction(e -> FormatMenuHelper.selectText(view, "<span style=\"background-color: #FFFF00\">", "</span>", highlightItem));
        menus.add(highlightItem);

        MenuItem superScriptItem = new MenuItem("SuperScript");
        superScriptItem.setOnAction(e -> FormatMenuHelper.selectText(view, "<sup>", "</sup>", superScriptItem));
        menus.add(superScriptItem);

        menus.add(new SeparatorMenuItem());

        MenuItem alignLeftItem = new MenuItem("Align Left");
        alignLeftItem.setOnAction(e -> FormatMenuHelper.selectText(view, "<p style=\"text-align: left;\">", "</p>", alignLeftItem));
        menus.add(alignLeftItem);

        MenuItem alignRightItem = new MenuItem("Align Right");
        alignRightItem.setOnAction(e -> FormatMenuHelper.selectText(view, "<p style=\"text-align: right;\">", "</p>", alignRightItem));
        menus.add(alignRightItem);

        MenuItem alignCenterItem = new MenuItem("Align Center");
        alignCenterItem.setOnAction(e -> FormatMenuHelper.selectText(view, "<p style=\"text-align: center;\">", "</p>", alignCenterItem));
        menus.add(alignCenterItem);

        menus.add(new SeparatorMenuItem());

        Menu imageMenu = new Menu("Image");

        MenuItem insertLocalImageItem = new MenuItem("Insert Local Image");
        insertLocalImageItem.setOnAction(e -> FormatMenuHelper.insertLocalImage(view));

        MenuItem decreaseImageSizeItem = new MenuItem("Decrease Image Size");
        decreaseImageSizeItem.setOnAction(e -> FormatMenuHelper.decreaseImageSize(view));

        imageMenu.getItems().addAll(insertLocalImageItem, new SeparatorMenuItem());
        menus.add(imageMenu);

        menus.add(new SeparatorMenuItem());

        MenuItem clearItem = new MenuItem("Clear Formatting");
        clearItem.setOnAction(e -> FormatMenuHelper.selectText(view, "", "", clearItem, "clear"));
        menus.add(clearItem);

        return menus;
    }

}
