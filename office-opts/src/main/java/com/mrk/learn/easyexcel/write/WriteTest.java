package com.mrk.learn.easyexcel.write;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class WriteTest {

    /**
     * 最简单的写
     * <p>1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>2. 直接写即可
     */
    @Test
    public void simpleWrite() {
        // 写法1
        String fileName = "src/main/resources/easyexcel"+ File.separator+"write" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, DemoData.class).sheet(1,"模板").doWrite(data(10));
//        EasyExcel.write(fileName, DemoData.class).sheet("模板").doWrite(data());


        // 写法2
        fileName = "src/main/resources/easyexcel"+ File.separator+"write" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写
        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcel.write(fileName, DemoData.class).build();
            WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
            excelWriter.write(data(10), writeSheet);
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    /**
     * 多sheet，不同数据结构
     */
    @Test
    public void multiSheetDemo() {
        File file = new File("src/main/resources/easyexcel" + File.separator + "write_multi" + System.currentTimeMillis() + ".xlsx");
        ExcelWriter build = EasyExcel.write(file).build();

        List<M1> list_1 = Arrays.asList(new M1("a", 1), new M1("b",2));
        WriteSheet writeSheet = EasyExcel.writerSheet(0, "sheet0").head(M1.class).build();
        build.write(list_1, writeSheet);

        List<M2> list_2 = Arrays.asList(new M2("x", 11), new M2("y",22));
        writeSheet = EasyExcel.writerSheet(1, "sheet1").head(M2.class).build();
        build.write(list_2, writeSheet);
        build.finish();
    }

    @Test
    public void multiSheetWrite() {
        String fileName = "src/main/resources/easyexcel"+ File.separator+"write" + System.currentTimeMillis() + ".xlsx";
        ExcelWriter excelWriter = EasyExcel.write(fileName, DemoData.class).build();
        // 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来。这里最终会写到5个sheet里面
        for (int i = 0; i < 5; i++) {
            // 每次都要创建writeSheet 这里注意必须指定sheetNo 而且sheetName必须不一样
            WriteSheet writeSheet = EasyExcel.writerSheet(i, "模板" + i).build();
            // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
            List<DemoData> data = data(10);
            excelWriter.write(data, writeSheet);
        }
        excelWriter.finish();
    }

    @Test
    public void multiSheetWrite2() {
        String fileName = "src/main/resources/easyexcel"+ File.separator+"write" + System.currentTimeMillis() + ".xlsx";
        ExcelWriter excelWriter = EasyExcel.write(fileName, DemoData.class).build();
        WriteSheet writeSheet = EasyExcel.writerSheet(0, "模板0").build();
        WriteSheet writeSheet1 = EasyExcel.writerSheet(1, "模板1").build();

        excelWriter.write( data(10), writeSheet);
        excelWriter.write( data(10), writeSheet1);
        excelWriter.write( data(10), writeSheet1);
        excelWriter.write( data(10), writeSheet);
        excelWriter.write( data(10), writeSheet1);
        excelWriter.write( data(10), writeSheet);
        excelWriter.write( data(10), writeSheet);

        excelWriter.finish();
    }
    @Test
    public void writeWithStyle() {
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景设置为红色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short)20);
        headWriteCellStyle.setWriteFont(headWriteFont);

        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
//        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        // 背景绿色
//        contentWriteCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        // 字体大小
//        WriteFont contentWriteFont = new WriteFont();
//        contentWriteFont.setFontHeightInPoints((short)20);
//        contentWriteCellStyle.setWriteFont(contentWriteFont);
        //边框
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);


        String fileName = "src/main/resources/easyexcel"+ File.separator+"write" + System.currentTimeMillis() + ".xlsx";
        ExcelWriter excelWriter = EasyExcel.write(fileName, DemoData.class).build();
        // 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来。这里最终会写到5个sheet里面
        for (int i = 0; i < 5; i++) {
            // 每次都要创建writeSheet 这里注意必须指定sheetNo 而且sheetName必须不一样
            WriteSheet writeSheet = EasyExcel.writerSheet(i, "模板" + i).registerWriteHandler(horizontalCellStyleStrategy).build();
            // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
            List<DemoData> data = data(10);
            excelWriter.write(data, writeSheet);
        }
        excelWriter.finish();
    }



    private List<DemoData> data(int length) {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < length; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }
}
