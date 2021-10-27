package com.mrk.learn.poitl;


import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.HyperlinkTextRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.Texts;
import com.deepoove.poi.data.style.Style;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * poitl示例
 * http://deepoove.com/poi-tl/#_getting_started
 */
public class Test2 {

    @Test
    public void t21() throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "helloworld");
        map.put("author", new TextRenderData("000000", "Sayi"));
        map.put("link", new HyperlinkTextRenderData("website", "http://deepoove.com"));
        map.put("anchor", new HyperlinkTextRenderData("anchortxt", "anchor:appendix1"));

        XWPFTemplate template = XWPFTemplate.compile("src/main/resources/poitl/t2.docx").render(map);
        template.writeAndClose(new FileOutputStream("src/main/resources/poitl/t2-out.docx"));
    }


    @Test
    public void t22() throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "helloworld");
        map.put("author", Texts.of("Sayi").style(Style.builder().buildColor("ff0000").build()).create());
        map.put("link", Texts.of("website").link("http://deepoove.com").create());
        map.put("anchor", Texts.of("anchortxt").anchor("appendix1").create());

        XWPFTemplate template = XWPFTemplate.compile("src/main/resources/poitl/t2.docx").render(map);
        template.writeAndClose(new FileOutputStream("src/main/resources/poitl/t2-out.docx"));
    }
}
