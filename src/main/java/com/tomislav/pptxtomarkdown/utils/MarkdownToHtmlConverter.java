package com.tomislav.pptxtomarkdown.utils;

import com.tomislav.pptxtomarkdown.css.Fonts;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MarkdownToHtmlConverter {

    public String convertMarkdownToHtml(String markdown, String... fontName) {
        Parser parser = Parser.builder().build();
        HtmlRenderer renderer = HtmlRenderer.builder().build();

        Document document = parser.parse(markdown);
        String html = renderer.render(document);


        String finalHtml = null;
        try {
            finalHtml = getHtmlTemplate(fontName[0], html);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return finalHtml;
    }

    private String getHtmlTemplate(String fontName, String content) throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/main-html.html");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder htmlTemplate = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            htmlTemplate.append(line);
        }

        String html = htmlTemplate.toString();
        html = html.replace("FONT_NAME", fontName);
        html = html.replace("CONTENT", content);

        return html;
    }


}
