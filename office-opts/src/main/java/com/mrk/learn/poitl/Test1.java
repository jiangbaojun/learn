package com.mrk.learn.poitl;


import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.util.PoitlIOUtils;
import org.junit.jupiter.api.Test;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

/**
 * poitl示例
 * 注意，woed模板中变量名称两端不能有空格，例如{{ title }}是不能识别的，要写成{{title}}
 * http://deepoove.com/poi-tl/#_getting_started
 */
public class Test1 {

    @Test
    public void t11() throws IOException {
        XWPFTemplate template = XWPFTemplate.compile("src/main/resources/poitl/t1.docx").render(
                new HashMap<String, Object>(){{
                    put("title", "HelloWorld");
                }});
        //写数据并关闭流，对比t12方法，详见writeAndClose的内部写法
        template.writeAndClose(new FileOutputStream("src/main/resources/poitl/t1-out.docx"));
    }

    @Test
    public void t12() throws IOException {
        XWPFTemplate template = XWPFTemplate.compile("src/main/resources/poitl/t1.docx").render(
                new HashMap<String, Object>(){{
                    put("title", "HelloWorld");
                }});
        //也可以先写数据，然后再关闭
        OutputStream out = new FileOutputStream("src/main/resources/poitl/t1-out.docx");
        BufferedOutputStream bos = new BufferedOutputStream(out);
        template.write(bos);
        bos.flush();
        out.flush();
        PoitlIOUtils.closeQuietlyMulti(template, bos, out);
    }
}
