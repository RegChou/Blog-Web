package com.muxui.blog.common.util;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.util.options.MutableDataSet;
import com.vladsch.flexmark.ext.tables.TablesExtension;

import java.util.Collections;

/**
 * @author ouyang
 * @title :
 * @description :
 * @createDate 2020/6/24
 */
public class Markdown2HtmlUtil {
    /**
     * Markdownz转为Html
     * @param content
     * @return
     */
    public static String html(String content){

        MutableDataSet options = new MutableDataSet();
        options.setFrom(ParserEmulationProfile.MARKDOWN);
        options.set(Parser.EXTENSIONS, Collections.singletonList(TablesExtension.create()));
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        Node document = parser.parse(content);

        return renderer.render(document);
    }
}
