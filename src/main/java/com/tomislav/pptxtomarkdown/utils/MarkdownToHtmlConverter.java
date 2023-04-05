package com.tomislav.pptxtomarkdown.utils;

import com.tomislav.pptxtomarkdown.css.Fonts;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;

public class MarkdownToHtmlConverter {

    public static String convertMarkdownToHtml(String markdown, String... fontName) {
        Parser parser = Parser.builder().build();
        HtmlRenderer renderer = HtmlRenderer.builder().build();

        Document document = parser.parse(markdown);
        String html = renderer.render(document);

        // Add MathJax script to the HTML header
        String mathJaxScript = "<script type=\"text/javascript\" async src=\"https://cdnjs.cloudflare.com/ajax/libs/mathjax/3.2.0/es5/tex-mml-chtml.js\"></script>";

        // Add CSS for font change
        String style = "<style>body { font-family: " + fontName[0]  + ", sans-serif; text-align: left; }</style>";
        String htmlWithFont = "<!DOCTYPE html><html><head>" + style + mathJaxScript + "</head><body>" + html + "</body></html>";

        return htmlWithFont;
    }

}
