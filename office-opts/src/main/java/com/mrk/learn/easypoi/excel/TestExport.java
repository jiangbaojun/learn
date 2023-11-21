package com.mrk.learn.easypoi.excel;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.excel.export.ExcelExportService;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/***
 * excel导出测试
 */
public class TestExport {

    public static void main(String[] args) throws IOException {
        //model数据
        ExportParams exportParams1 = new ExportParams();
        exportParams1.setType(ExcelType.XSSF);
        exportParams1.setSheetName("sheet1");
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams1, ExportPermission.class, getData2());

        //map类型数据
        ExportParams exportParams2 = new ExportParams();
        exportParams2.setType(ExcelType.XSSF);
        exportParams2.setSheetName("sheet2");
        exportParams2.setDataHandler(new TestDateFieldDataHandler(Arrays.asList("列名称-0")));
        List<ExcelExportEntity> entityList = getEntityList();
        new ExcelExportService().createSheetForMap(workbook, exportParams2, entityList, getData1(entityList));

        //使用dataHandler
        ExportParams exportParams3 = new ExportParams();
        exportParams3.setType(ExcelType.XSSF);
        exportParams3.setSheetName("sheet3");
        exportParams3.setDataHandler(new TestDateFieldDataHandler(ExportPermission.class));
        new ExcelExportService().createSheet(workbook, exportParams3, ExportPermission.class, getData2());

        workbook.write(new FileOutputStream("C:\\Users\\BaojunJiang\\Desktop\\234.xlsx"));
    }

    private static List<ExportPermission> getData2() {
        List<ExportPermission> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ExportPermission e = new ExportPermission();
            e.setPermissions("aa"+i);
            e.setUrl("http://"+i+".com");
            e.setCreateDate(new Date());
            list.add(e);
        }
        return list;
    }

    /**
     * 表头
     */
    private static List<ExcelExportEntity> getEntityList() {
        List<ExcelExportEntity> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new ExcelExportEntity("列名称-"+i, "key-"+i));
        }
        return list;
    }

    /**
     * 表格数据
     */
    private static List<Map<String,Object>> getData1(List<ExcelExportEntity> entityList) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Map<String, Object> map = new HashMap<>();
            for (ExcelExportEntity excelExportEntity : entityList) {
                map.put(excelExportEntity.getKey().toString(), i);
            }
            //第一列设置为Date值
            map.put("key-0", new Date());
            list.add(map);
        }
        return list;
    }
}
