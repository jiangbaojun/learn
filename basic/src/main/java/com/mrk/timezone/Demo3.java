package com.mrk.timezone;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class Demo3 {

    @Test
    public void test8() {
        // 本地日期/时间
        System.out.println("================本地时间================");
        System.out.println(LocalDate.now());
        System.out.println(LocalTime.now());
        System.out.println(LocalDateTime.now());

        // 时区时间
        System.out.println("================带时区的时间ZonedDateTime================");
        System.out.println(ZonedDateTime.now()); // 使用系统时区
        System.out.println(ZonedDateTime.now(ZoneId.of("America/New_York"))); // 自己指定时区
        System.out.println(ZonedDateTime.now(Clock.systemUTC())); // 自己指定时区

        System.out.println("================带时区的时间OffsetDateTime================");
        System.out.println(OffsetDateTime.now()); // 使用系统时区
        System.out.println(OffsetDateTime.now(ZoneId.of("America/New_York"))); // 自己指定时区
        System.out.println(OffsetDateTime.now(Clock.systemUTC())); // 自己指定时区
    }

    @Test
    public void test11() {
        String dateTimeStrParam = "2021-05-05T18:00:06";
        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeStrParam);
        System.out.println("解析后：" + localDateTime);
    }

    @Test
    public void test12() {
        // 带偏移量 使用OffsetDateTime
        String dateTimeStrParam = "2021-05-05T18:00-04:00";
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(dateTimeStrParam);
        System.out.println("带偏移量解析后：" + offsetDateTime);

        // 带时区 使用ZonedDateTime
        dateTimeStrParam = "2021-05-05T18:00-05:00[America/New_York]";
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateTimeStrParam);
        System.out.println("带时区解析后：" + zonedDateTime);
    }

    @Test
    public void test13() {
        System.out.println(DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDate.now()));
        System.out.println(DateTimeFormatter.ISO_LOCAL_TIME.format(LocalTime.now()));
        System.out.println(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now()));
    }
    @Test
    public void test14() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("第Q季度 yyyy-MM-dd HH:mm:ss");
        // 格式化输出
        System.out.println(formatter.format(LocalDateTime.now()));
        System.out.println(formatter.format(LocalDateTime.now(ZoneId.of("America/New_York"))));
        // 解析
        String dateTimeStrParam = "第1季度 2021-01-17 22:51:32";
        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeStrParam, formatter);
        System.out.println("解析后的结果：" + localDateTime);
    }

}
