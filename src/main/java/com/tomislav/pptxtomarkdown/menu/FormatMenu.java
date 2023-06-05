package com.tomislav.pptxtomarkdown.menu;

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
        strongItem.setOnAction(e -> selectText(view, "**", "**", "\\**"));
        menus.add(strongItem);

        MenuItem emphasisItem = new MenuItem("Emphasis");
        emphasisItem.setOnAction(e -> selectText(view, "*", "*", "\\*"));
        menus.add(emphasisItem);

        MenuItem underlineItem = new MenuItem("Underline");
        underlineItem.setOnAction(e -> selectText(view, "<u>", "</u>"));
        menus.add(underlineItem);

        menus.add(new SeparatorMenuItem());

        MenuItem strikeItem = new MenuItem("Strike");
        strikeItem.setOnAction(e -> selectText(view, "<s>", "</s>"));
        menus.add(strikeItem);

        MenuItem highlightItem = new MenuItem("Highlight");
        highlightItem.setOnAction(e -> selectText(view, "<span style=\"background-color: #FFFF00\">", "</span>"));
        menus.add(highlightItem);

        MenuItem superScriptItem = new MenuItem("SuperScript");
        superScriptItem.setOnAction(e -> selectText(view, "<sup>", "</sup>"));
        menus.add(superScriptItem);

        menus.add(new SeparatorMenuItem());

        MenuItem clearItem = new MenuItem("Clear Formatting");
        clearItem.setOnAction(e -> selectText(view, "", "", "clear"));
        menus.add(clearItem);

        return menus;
    }

    private static void selectText(PptxToMarkDownView view, String formatTypeStart, String formatTypeEnd, String... markdownMark) {
        try {
            if(markdownMark.length != 0){
                if(markdownMark[0].equals("clear")){
                    IndexRange range = view.getMarkdownOutput().getSelection();
                    String selectedText = view.getMarkdownOutput().getSelectedText().replaceAll(markdownMark[0], "").trim();
                    String clearedText = selectedText.replaceAll("[*]|_|<u>|</u>|<s>|</s>|<span style=\"background-color: #FFFF00\">|</span>|<sup>|</sup>", "");
                    view.getMarkdownOutput().replaceText(range, clearedText);
                }
            }

            String selectedText = view.getMarkdownOutput().getSelectedText().replaceAll(markdownMark.length == 0 ? "" : markdownMark[0], "").trim();
            int selectionStart = view.getMarkdownOutput().getSelection().getStart();
            int selectionEnd = view.getMarkdownOutput().getSelection().getEnd();

            // Replace the selected text with a markdown heading of the corresponding level
            String formattedText = String.join("", formatTypeStart, selectedText, formatTypeEnd);

            view.getMarkdownOutput().replaceText(selectionStart, selectionEnd, formattedText);
        }
        catch (Exception e) {
            NotificationManager.showMessageBox(e.getMessage(), Alert.AlertType.ERROR, new Duration(3000));
        }

    }
}
