package com.tomislav.pptxtomarkdown.utils;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;

public class MarkdownToHtmlConverter {

    public static String convertMarkdownToHtml(String markdown) {
        Parser parser = Parser.builder().build();
        HtmlRenderer renderer = HtmlRenderer.builder().build();

        Document document = parser.parse(markdown);
        String html = renderer.render(document);

        // Dodavanje CSS-a za promjenu fonta
        String style = "<style>body { font-family: 'Cambria', sans-serif; }</style>";
        String htmlWithFont = "<!DOCTYPE html><html><head>" + style + "</head><body>" + html + "</body></html>";

        return htmlWithFont;
    }
}
