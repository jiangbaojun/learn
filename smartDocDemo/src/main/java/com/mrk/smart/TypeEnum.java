package com.mrk.smart;

/**
 * 商品分类
 * @date 2023/2/1 17:56
 */
public enum TypeEnum {

    MOBILE(1, "手机"),
    COMPUTER(2, "电脑"),
    CAR(3, "车品"),
    FOOD(4, "食品");

    private Integer code;

    private String desc;

    TypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return this.code;
    }


    public String getDesc() {
        return this.desc;
    }
}