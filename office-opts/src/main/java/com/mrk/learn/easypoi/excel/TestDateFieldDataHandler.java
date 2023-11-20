package com.mrk.learn.easypoi.excel;


import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.handler.impl.ExcelDataHandlerDefaultImpl;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 日期类型自定义处理
 */
public class TestDateFieldDataHandler extends ExcelDataHandlerDefaultImpl {

    private final Class<?> pojoClass;
    private final Map<String, String[]> classNeedFieldMap = new HashMap();

    public TestDateFieldDataHandler(Class<?> pojoClass) {
        this.pojoClass = pojoClass;
    }

    /**
     * 查找需要处理的字段（列）
     * @apiNote 目前只处理日期类型
     * @return java.lang.String[]
     * @date 2023/10/25 16:55
     */
    @Override
    public String[] getNeedHandlerFields() {
        String className = pojoClass.getName();
        String[] needFieldsArray = classNeedFieldMap.get(className);
        if(needFieldsArray!=null){
            return needFieldsArray;
        }
        List<Field> declaredFields = getFieldAll(pojoClass);
        List<String> needFieldNameList = new ArrayList<>();
        for (Field declaredField : declaredFields) {
            if(!Date.class.equals(declaredField.getType())){
                //不处理非日期类型字段
                continue;
            }
            Excel[] annotationsByType = declaredField.getAnnotationsByType(Excel.class);
            if(annotationsByType.length == 0){
                //没有使用Excel注解注释的date字段
                continue;
            }
            Excel excelAnnotation = annotationsByType[0];
            String excelEntityName = excelAnnotation.name();
            needFieldNameList.add(excelEntityName);
        }
        needFieldsArray = needFieldNameList.stream().toArray(String[]::new);
        classNeedFieldMap.put(className, needFieldsArray);
        return needFieldsArray;
    }

    /**
     * 数据值导出处理
     * @return java.lang.Object
     * @date 2023/10/25 16:55
     */
    @Override
    public Object exportHandler(Object obj, String name, Object value) {
        if(value==null){
            return null;
        }
        if(!(value instanceof Date)){
            return value;
        }
        return new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss").format(value);
    }

    /**
     * 获得类字段
     * @param paramClass 类型
     * @date 2023/10/25 16:32
     */
    private List<Field> getFieldAll(Class<?> paramClass) {
        List<Field> fieldList = new ArrayList<>();
        Class<?> superclass = paramClass;
        while (superclass!=null){
            try {
                fieldList.addAll(Arrays.stream(superclass.getDeclaredFields()).collect(Collectors.toList()));
            } catch (Exception ignored) {}
            superclass = superclass.getSuperclass();
        }
        return fieldList;
    }
}
