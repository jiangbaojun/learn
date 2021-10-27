package com.mrk.learn.conversion.jacob.others;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class JacobUtil
{
    //常量定义API:https://docs.microsoft.com/zh-cn/office/vba/api/excel.xlfileformat
    public static final int EXCEL_HTML = 44;
    public static final int EXCEL_PDF = 57;

    public static void main(String [] args)
    {
        excelToPdf("D:\\1.xlsx","D:\\1.pdf");
    }

    /**
     * EXCEL转pdf
     * @param xlsfile EXCEL文件全路径
     * @param pdffile 转换后pdf存放路径
     */
    public static void excelToPdf(String xlsfile, String pdffile) {
        // 启动excel
        ActiveXComponent app = new ActiveXComponent("Excel.Application");
        try {
              // 设置excel不可见
              app.setProperty("Visible", new Variant(false));
              // 禁用宏
              app.setProperty("AutomationSecurity", new Variant(3));
              // 得到工作表
              Dispatch excels = app.getProperty("Workbooks").toDispatch();
              // 打开excel文件
              // API资料:https://docs.microsoft.com/zh-cn/office/vba/api/excel.workbooks.open
                Dispatch excel = Dispatch.invoke(excels,"Open",Dispatch.Method,new Object[] {
                      xlsfile,
                      new Variant(false),
                      new Variant(false)
                },new int[9]).toDispatch();
              // 获取excel表中的sheet集合
                // API资料:https://docs.microsoft.com/zh-cn/office/vba/api/excel.worksheets
              Dispatch sheets = Dispatch.call(excel, "Worksheets").toDispatch();
              // 获取sheet集合中的第一个sheet
              // API资料:https://docs.microsoft.com/zh-cn/office/vba/api/excel.worksheets.item
              Dispatch sheet = Dispatch.call(sheets, "Item", new Integer(1)).toDispatch();
              // 获取第一个sheet的页面设置对象
              // API资料:https://docs.microsoft.com/zh-cn/office/vba/api/excel.pagesetup
              Dispatch pageSetup = Dispatch.call(sheet, "PageSetup").toDispatch();
              // 将excel表格 设置成A3的大小
              // API资料:https://docs.microsoft.com/zh-cn/office/vba/api/excel.pagesetup.papersize
              Dispatch.put(pageSetup, "PaperSize", new Integer(8));//A3是8，A4是9，A5是11等等
              // 缩放值为100或false
              // API资料:https://docs.microsoft.com/zh-cn/office/vba/api/excel.pagesetup.zoom
              Dispatch.put(pageSetup, "Zoom", false);
              // 所有列为一页(1或false)
              // API资料:https://docs.microsoft.com/zh-cn/office/vba/api/excel.pagesetup.fittopageswide
              Dispatch.put(pageSetup, "FitToPagesWide", 1);
              // 设置上边距
              // API资料:https://docs.microsoft.com/zh-cn/office/vba/api/excel.pagesetup.topmargin
              Dispatch.put(pageSetup, "TopMargin", 0);
              // 设置右边距
              // API资料:https://docs.microsoft.com/zh-cn/office/vba/api/excel.pagesetup.rightmargin
              Dispatch.put(pageSetup, "RightMargin", 0);
              // 设置左边距
              // API资料:https://docs.microsoft.com/zh-cn/office/vba/api/excel.pagesetup.leftmargin
              Dispatch.put(pageSetup, "LeftMargin", 0);
              // 作为pdf格式保存到临时文件
              // API资料:https://docs.microsoft.com/zh-cn/office/vba/api/excel.workbook.exportasfixedformat
                Dispatch.invoke(excel,"ExportAsFixedFormat",Dispatch.Method,new Object[]{
                      new Variant(0), // PDF格式=0 常量描述:https://docs.microsoft.com/zh-cn/office/vba/api/excel.xlfixedformattype
                      pdffile,
                      new Variant(0)  // 0=标准 (生成的PDF图片不会变模糊) 1=最小文件 (生成的PDF图片糊的一塌糊涂)
                },new int[1]);
                // 关闭excel
              Dispatch.call(excel, "Close", new Variant(false));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭excel程序
            app.invoke("Quit", new Variant[] {});
        }
    }

    /**
     * EXCEL转HTML
     * @param xlsfile EXCEL文件全路径
     * @param htmlfile 转换后HTML存放路径
     */
    public static void excelToHtml(String xlsfile, String htmlfile) {
        // 启动excel
        ActiveXComponent app = new ActiveXComponent("Excel.Application");
        try {
              // 设置excel不可见
              app.setProperty("Visible", new Variant(false));
              Dispatch excels = app.getProperty("Workbooks").toDispatch();
              // 打开excel文件
              // API资料:https://docs.microsoft.com/zh-cn/office/vba/api/excel.workbooks.open
              Dispatch excel = Dispatch.invoke(
                        excels,
                        "Open",
                        Dispatch.Method,
                        new Object[] { xlsfile, new Variant(false),
                                new Variant(true) }, new int[1]).toDispatch();
              // 作为html格式保存到临时文件
              // API资料:https://docs.microsoft.com/zh-cn/office/vba/api/excel.workbook.saveas
              Dispatch.invoke(excel, "SaveAs", Dispatch.Method, new Object[] {
                        htmlfile,
                        new Variant(EXCEL_HTML) // 类型常量:https://docs.microsoft.com/zh-cn/office/vba/api/excel.xlfileformat
              }, new int[1]);
              Variant f = new Variant(false);
              // 关闭excel文档
              Dispatch.call(excel, "Close", f);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 退出excel程序
            app.invoke("Quit", new Variant[] {});
        }
    }
}
