package com.mrk.timezone;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.Set;
import java.util.TimeZone;

public class Demo2 {

    @Test
    public void test3() {
        Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
        System.out.println("可用zoneId总数：" + availableZoneIds.size());
        for (String zoneId : availableZoneIds) {
            System.out.println(zoneId);
        }
    }
    @Test
    public void test2() {
        System.out.println(ZoneId.of("Asia/Shanghai"));

        ZoneId zoneId = ZoneId.ofOffset("UTC", ZoneOffset.of("+8"));
        System.out.println(zoneId);

        zoneId = ZoneId.ofOffset("UTC", ZoneOffset.of("+10:00:00"));
        System.out.println(zoneId);

        // 必须是大写的Z
        zoneId = ZoneId.ofOffset("UTC", ZoneOffset.of("Z"));
        System.out.println(zoneId);

        //从日期中获取时区
        System.out.println(ZoneId.from(ZonedDateTime.now()));
        System.out.println(ZoneId.from(ZoneOffset.of("+8")));
    }

    @Test
    public void test1() {
        // JDK 1.8之前做法
        System.out.println(TimeZone.getDefault());
        // JDK 1.8之后做法
        System.out.println(ZoneId.systemDefault());
    }

    @Test
    public void test6() {
        System.out.println("最小偏移量：" + ZoneOffset.MIN);
        System.out.println("最小偏移量：" + ZoneOffset.MAX);
        System.out.println("中心偏移量：" + ZoneOffset.UTC);
        // 超出最大范围
        System.out.println(ZoneOffset.of("+20"));
    }

}
