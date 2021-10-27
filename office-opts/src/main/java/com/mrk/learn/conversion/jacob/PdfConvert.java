package com.mrk.learn.conversion.jacob;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import org.junit.jupiter.api.Test;

public class PdfConvert {
    private String base = "D:/myProject/learn/learn/office-opts/src/main/resources/jacob/";

    @Test
    public void wordPdfConvert() {
        String path = base+"t1.docx";
        String path2 = base+"t1.pdf";
        word2PDF(path, path2);
    }
    @Test
    public void xlsxPdfConvert() {
        String path5 = base+"t2.xlsx";
        String path6 = base+"t2.pdf";
        excel2PDF(path5, path6);
    }
    @Test
    public void pptxPdfConvert() {
        String path3 = base+"t3.pptx";
        String path4 = base+"t3.pdf";
        ppt2PDF(path3, path4);
    }

    public boolean word2PDF(String inputFile, String pdfFile) {
        ActiveXComponent app = new ActiveXComponent("Word.Application");
        try {
            app.setProperty("Visible", false);
            Dispatch docs = app.getProperty("Documents").toDispatch();
            Dispatch doc = Dispatch.call(docs, "Open", new Object[]{inputFile, false, true}).toDispatch();

//            Dispatch.call(doc, "ExportAsFixedFormat", new Object[]{pdfFile, 17});
            //最后一个参数1，表示导出的pdf含有标签
            Dispatch.call(doc, "ExportAsFixedFormat", new Object[]{
                    pdfFile, 17, false, 0, 0, 0, 0, 7, true, true, 1
            });

            Dispatch.call(doc, "Close", new Object[]{false});
            app.invoke("Quit", 0);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            app.invoke("Quit", 0);
            return false;
        }
    }

    public boolean excel2PDF(String inputFile, String pdfFile) {
        ComThread.InitSTA(true);
        ActiveXComponent app = new ActiveXComponent("Excel.Application");
        try {
            app.setProperty("Visible", false);
            app.setProperty("AutomationSecurity", new Variant(3));
            Dispatch excels = app.getProperty("Workbooks").toDispatch();
            Dispatch excel = Dispatch.invoke(excels, "Open", 1, new Object[]{inputFile, new Variant(false), new Variant(false)}, new int[9]).toDispatch();

            boolean hasNextSheet = true;
            int i = 1;  //防止意外情况，最大处理255张表
            while(hasNextSheet && i<=255){
                try{
                    Dispatch sheets = Dispatch.call(excel, "Worksheets").toDispatch();
                    Dispatch sheet = Dispatch.call(sheets, "Item", new Integer(i)).toDispatch();
                    pageSetup(sheet);
                    i++;
                }catch (Exception e){
                    hasNextSheet = false;
                }
            }

            Dispatch.invoke(excel, "ExportAsFixedFormat", 1, new Object[]{new Variant(0), pdfFile, new Variant(0)}, new int[1]);
            Dispatch.call(excel, "Close", new Object[]{false});
            if (app != null) {
                app.invoke("Quit", new Variant[0]);
                app = null;
            }
            ComThread.Release();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            app.invoke("Quit");
            return false;
        }
    }

    private void pageSetup(Dispatch sheet) {
        Dispatch pageSetup = Dispatch.call(sheet, "PageSetup").toDispatch();
        Dispatch.put(pageSetup, "zoom", false);
        Dispatch.put(pageSetup, "FitToPagesWide", 1);
        Dispatch.put(pageSetup, "FitToPagesTall", false);
    }

    public boolean ppt2PDF(String inputFile, String pdfFile) {
        inputFile = "D:\\myProject\\learn\\learn\\office-opts\\src\\main\\resources\\jacob\\t3.pptx";
        pdfFile = "D:\\myProject\\learn\\learn\\office-opts\\src\\main\\resources\\jacob\\t3.pdf";

        ActiveXComponent app = new ActiveXComponent("PowerPoint.Application");

        try {
            Dispatch ppts = app.getProperty("Presentations").toDispatch();
            Dispatch ppt = Dispatch.call(ppts, "Open", new Object[]{inputFile, true, true, false}).toDispatch();
            Dispatch.call(ppt, "SaveAs", new Object[]{pdfFile, 32});
            Dispatch.call(ppt, "Close");
            app.invoke("Quit");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            app.invoke("Quit");
            return false;
        }
    }
}
