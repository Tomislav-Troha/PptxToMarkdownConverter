package com.tomislav.pptxtomarkdown.utils;

import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.MutableDataSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class MarkdownToHtmlConverter {

    public String convertMarkdownToHtml(String markdown) {

        MutableDataSet options = new MutableDataSet();

        options.set(Parser.EXTENSIONS, List.of(TablesExtension.create()));

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        Document document = parser.parse(markdown);
        String html = renderer.render(document);


        String finalHtml = null;
        try {
            finalHtml = getHtmlTemplate(html);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return finalHtml;
    }

    private String getHtmlTemplate(String content) throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/main-html.html");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder htmlTemplate = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            htmlTemplate.append(line);
        }

        String html = htmlTemplate.toString();
        html = html.replace("CONTENT", content);

        return html;
    }


}
