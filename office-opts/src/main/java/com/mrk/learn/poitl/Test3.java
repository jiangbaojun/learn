package com.mrk.learn.poitl;


import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.Rows;
import com.deepoove.poi.data.Tables;
import com.deepoove.poi.data.style.TableStyle;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * poitl示例
 * 要注意，表格标签以#开始,例如：{{#table1}}
 * http://deepoove.com/poi-tl/#_getting_started
 */
public class Test3 {

    @Test
    public void t31() throws IOException {
        XWPFTemplate template = XWPFTemplate.compile("src/main/resources/poitl/t3.docx").render(
                new HashMap<String, Object>(){{
                    put("table0", Tables.of(new String[][] {
                            new String[] { "00", "01" },
                            new String[] { "10", "11" }
                    }).border(TableStyle.BorderStyle.DEFAULT).create());
                }});
        template.writeAndClose(new FileOutputStream("src/main/resources/poitl/t3-out.docx"));
    }


    @Test
    public void t32() throws IOException {
        XWPFTemplate template = XWPFTemplate.compile("src/main/resources/poitl/t3.docx").render(
                new HashMap<String, Object>(){{
                    // 第0行居中且背景为蓝色的表格
                    RowRenderData row0 = Rows.of("姓名", "学历").textColor("FFFFFF")
                            .bgColor("4472C4").center().create();
                    RowRenderData row1 = Rows.create("李四", "博士");
                    put("table1", Tables.create(row0, row1));
                }});
        template.writeAndClose(new FileOutputStream("src/main/resources/poitl/t3-out.docx"));
    }

    @Test
    public void t33() throws IOException {
        XWPFTemplate template = XWPFTemplate.compile("src/main/resources/poitl/t3.docx").render(
                new HashMap<String, Object>(){{
                    //表格变量
                    put("table0", Tables.of(new String[][] {
                            new String[] { "00", "01" },
                            new String[] { "10", "11" }
                    }).border(TableStyle.BorderStyle.DEFAULT).create());
                    //普通变量
                    put("var1", "我是var1");
                    put("var2", "你是var2");
                }});
        template.writeAndClose(new FileOutputStream("src/main/resources/poitl/t3-out.docx"));
    }
}
