package com.tomislav.pptxtomarkdown.menu;

import com.tomislav.pptxtomarkdown.view.PptxToMarkDownView;
import javafx.application.Platform;
import javafx.scene.control.IndexRange;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParagraphMenu {

    public static List<MenuItem> createParagraphMenuItem(PptxToMarkDownView view) {
        List<MenuItem> menuItems = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            MenuItem menuItem = new MenuItem("Heading " + i);
            menuItems.add(menuItem);

            int finalI = i;
            menuItem.setOnAction(event -> {
                String selectedText = view.getMarkdownOutput().getSelectedText().replaceAll("#+", "").trim();

                if (selectedText.isEmpty()) return;

                int selectionStart = view.getMarkdownOutput().getSelection().getStart();
                int selectionEnd = view.getMarkdownOutput().getSelection().getEnd();

                String heading = String.join("", Collections.nCopies(finalI, "#"));

                if (selectedText.startsWith(heading)) return;

                String formattedText = String.join(" ", heading, selectedText);

                // Replace the selected text with the formatted text
                view.getMarkdownOutput().replaceText(selectionStart, selectionEnd, formattedText);
            });
        }

        MenuItem mathBlockMenuItem = createMathBlockMenuItem(view);
        menuItems.add(new SeparatorMenuItem());
        menuItems.add(mathBlockMenuItem);
        menuItems.add(new SeparatorMenuItem());
        menuItems.add(createTableMenuItem(view));
        menuItems.add(new SeparatorMenuItem());
        menuItems.add(createCodeSnippetMenuItem(view));

        return menuItems;
    }


    private static MenuItem createMathBlockMenuItem(PptxToMarkDownView view) {
        MenuItem menuItem = new MenuItem("Math Block");
        menuItem.setOnAction(event -> {
            String selectedText = view.getMarkdownOutput().getSelectedText().trim();
            int selectionStart = view.getMarkdownOutput().getSelection().getStart();
            int selectionEnd = view.getMarkdownOutput().getSelection().getEnd();

            // Replace the selected text with a markdown heading of the corresponding level
            String formattedText = String.join(" ", "$$ ", selectedText, " $$");

            // Replace the selected text with the formatted text
            view.getMarkdownOutput().replaceText(selectionStart, selectionEnd, formattedText);
        });
        return menuItem;
    }

    private static MenuItem createTableMenuItem(PptxToMarkDownView view){
        MenuItem insertTable = new MenuItem("Insert Table");
        insertTable.setOnAction(e -> {
            String tableMarkdown = "| Column 1 | Column 2 |\n| -------- | -------- |\n| Data 1   | Data 2   |\n";
            // get the current caret position
            int caretPosition = view.getMarkdownOutput().getCaretPosition();
            // insert the table markdown at the caret position
            view.getMarkdownOutput().insertText(caretPosition, tableMarkdown);
        });
        return insertTable;
    }

    private static MenuItem createCodeSnippetMenuItem(PptxToMarkDownView view) {
        MenuItem menuItem = new MenuItem("Insert Code Snippet");
        menuItem.setOnAction(event -> {
            IndexRange range = view.getMarkdownOutput().getSelection();
            String selectedText = view.getMarkdownOutput().getSelectedText();
            String codeBlock = "```\n" + selectedText + "\n```";
            view.getMarkdownOutput().replaceText(range.getStart(), range.getEnd(), codeBlock);
        });
        return menuItem;
    }

}
