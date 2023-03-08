package com.mrk.timezone;

import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Demo {
    public Demo() {
    }

    @Test
    public void t1(){
        String[] availableIDs = TimeZone.getAvailableIDs();
        System.out.println("可用zoneId总数：" + availableIDs.length);
        for (String zoneId : availableIDs) {
            System.out.println(zoneId);
        }
    }

    @Test
    public void test3() {
        System.out.println(TimeZone.getTimeZone("GMT+08:00").getID());
        System.out.println(TimeZone.getDefault().getID());

        // 纽约时间
        System.out.println(TimeZone.getTimeZone("GMT-05:00").getID());
        System.out.println(TimeZone.getTimeZone("America/New_York").getID());
    }

    /**
     * 设置默认时区
     * API方式： 强制将时区设为北京时区TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
     * JVM参数方式：-Duser.timezone=GMT+8
     * 运维设置方式：将操作系统主机时区设置为北京时区，这是推荐方式，可以完全对开发者无感，也方便了运维统一管理
     */
    @Test
    public void test4() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Hong_Kong"));
        System.out.println(TimeZone.getTimeZone("GMT+08:00").getID());
        System.out.println(TimeZone.getDefault().getID());
    }

    @Test
    public void test6() {
        String patterStr = "yyyy-MM-dd HH:mm:ss";
        Date currDate = new Date(System.currentTimeMillis());

        // 北京时区
        DateFormat bjDateFormat = new SimpleDateFormat(patterStr);
        bjDateFormat.setTimeZone(TimeZone.getDefault());
        // 纽约时区
        DateFormat newYorkDateFormat = new SimpleDateFormat(patterStr);
        newYorkDateFormat.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        // 伦敦时区
        DateFormat londonDateFormat = new SimpleDateFormat(patterStr);
        londonDateFormat.setTimeZone(TimeZone.getTimeZone("Europe/London"));

        System.out.println("毫秒数:" + currDate.getTime() + ", 北京本地时间:" + bjDateFormat.format(currDate));
        System.out.println("毫秒数:" + currDate.getTime() + ", 纽约本地时间:" + newYorkDateFormat.format(currDate));
        System.out.println("毫秒数:" + currDate.getTime() + ", 伦敦本地时间:" + londonDateFormat.format(currDate));
    }

}
