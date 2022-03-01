package com.mrk.learn.conversion.jacob;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import org.junit.jupiter.api.Test;

/**
 * @author jiangbaojun
 * @version v1.0
 * @workid 1861
 * @date 2022/2/24 09:04
 */
public class CompareDoc {

    /**
     * 比较doc
     */
    @Test
    public void compareDocx() {
        String inputFile = "C:/Users/jiang/Desktop/_temp/2.docx";
        String targetFile = "C:/Users/jiang/Desktop/_temp/1.docx";
        String saveFile = "C:/Users/jiang/Desktop/_temp/compare.docx";
        ActiveXComponent app = new ActiveXComponent("Word.Application");
        try {
            app.setProperty("Visible", false);
            Dispatch docs = app.getProperty("Documents").toDispatch();
            Dispatch doc = Dispatch.call(docs, "Open", new Object[]{inputFile, false, true}).toDispatch();
//            Dispatch docTarget = Dispatch.call(docs, "Open", new Object[]{targetFile, false, true}).toDispatch();

            Dispatch.call(doc, "Compare", new Object[]{
                    targetFile, new Variant("xiaoming"), new Variant(1), true
            });
            Dispatch.call(doc, "SaveAs2", new Object[]{saveFile});
            Dispatch.call(doc, "Close", new Object[]{false});
            app.invoke("Quit", 0);

            new PdfConvert().word2PDF(saveFile,"C:/Users/jiang/Desktop/_temp/compare.pdf");

        } catch (Exception e) {
            e.printStackTrace();
            app.invoke("Quit", 0);
        }
    }

    /**
     * 转html
     */
    @Test
    public void test() {
        String inputFile = "C:/Users/jiang/Desktop/_temp/compare.docx";
        ActiveXComponent app = new ActiveXComponent("Word.Application");
        try {
            app.setProperty("Visible", false);
            Dispatch docs = app.getProperty("Documents").toDispatch();
            Dispatch doc = Dispatch.call(docs, "Open", new Object[]{inputFile, false, true}).toDispatch();

            Dispatch.call(doc, "SaveAs2", new Object[] {
                    new Variant("C:/Users/jiang/Desktop/_temp/compare.html"),
                    new Variant(8)
            });

//            Dispatch.invoke(doc, "SaveAs2", Dispatch.Method, new Object[] {
//                    "C:/Users/jiang/Desktop/_temp/compare.html", new Variant(8) }, new int[1]);

            Dispatch.call(doc, "Close", new Object[]{false});
            app.invoke("Quit", 0);
        } catch (Exception e) {
            e.printStackTrace();
            app.invoke("Quit", 0);
        }
    }
}
