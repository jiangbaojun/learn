package com.mrk.learn.md.common;

public enum DataSourceEnum {

    DS1("demo_ds_1"),
    DS2("demo_ds_2"),
    DS3("demo_ds_3");

    private String value;

    DataSourceEnum(String value){this.value=value;}

    public String getValue() {
        return value;
    }
}
