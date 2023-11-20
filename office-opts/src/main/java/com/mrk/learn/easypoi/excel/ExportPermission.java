package com.mrk.learn.easypoi.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 统计权限使用信息
 * @author baojun.jiang
 * @date 2022/8/19 9:20
 */
@Data
public class ExportPermission implements Serializable {
    /**系统*/
    @Excel(name = "系统ID")
    private Integer system;

    /**请求路径*/
    @Excel(name = "请求地址")
    private String url;

    /**请求class*/
    @Excel(name = "类")
    private String clazz;

    /**请求方法*/
    @Excel(name = "方法")
    private String method;

    /**请求所需权限*/
    @Excel(name = "权限")
    private String permissions;

    /**请求业务说明*/
    @Excel(name = "描述")
    private String description;

    /**创建时间*/
    @Excel(name = "创建时间")
    private Date createDate;

}
