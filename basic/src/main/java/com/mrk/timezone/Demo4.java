package com.mrk.timezone;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

public class Demo4 {

    /**
     * LocalDateTime和Date互转
     */
    @Test
    public void test20() {
        ZoneId systemDefault = ZoneId.systemDefault();
        Date date = new Date();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), systemDefault);
        System.out.println(localDateTime);

        ZonedDateTime zonedDateTime = LocalDateTime.now().atZone(systemDefault);
        Date date1 = Date.from(zonedDateTime.toInstant());
        System.out.println(date1);
    }

    /**
     * 时区转换及夏令时
     */
    @Test
    public void test19() {
        ZoneId zoneId = ZoneId.of("America/New_York");
        ZoneId zoneIdTokyo = ZoneId.of("Asia/Tokyo");

        //非夏令时
        LocalDateTime dt = LocalDateTime.of(2020,02,12,15, 20, 30);
        ZonedDateTime defaultZoneDt = dt.atZone(ZoneId.systemDefault());
        ZonedDateTime zonedDateTime = defaultZoneDt.withZoneSameInstant(zoneId);
        System.out.println(dt);
        System.out.println(defaultZoneDt);
        System.out.println(zonedDateTime);
        System.out.println(Duration.between(defaultZoneDt.toLocalDateTime(), zonedDateTime.toLocalDateTime()));

        System.out.println("=============================================================");

        //夏令时
        LocalDateTime dt1 = LocalDateTime.of(2020,05,12,15, 20, 30);
        ZonedDateTime defaultZoneDt1 = dt1.atZone(ZoneId.systemDefault());
        ZonedDateTime zonedDateTime1 = defaultZoneDt1.withZoneSameInstant(zoneId);
        System.out.println(dt1);
        System.out.println(defaultZoneDt1);
        System.out.println(zonedDateTime1);
        System.out.println(Duration.between(defaultZoneDt1.toLocalDateTime(), zonedDateTime1.toLocalDateTime()));
    }

    @Test
    public void test18() {
        //Duration 使用基于时间的值(秒，纳秒)测量时间量。Period 使用基于日期的值(年，月，日)。
        System.out.println(Duration.ofHours(10)); // 10 hours
        System.out.println(Duration.parse("P1DT2H3M")); // 1 day, 2 hours, 3 minutes

        System.out.println(Period.ofDays(10));
        System.out.println(Period.parse("P1Y2M3D"));
    }
    @Test
    public void test17() {
        LocalDateTime start = LocalDateTime.of(2019, 11, 19, 18, 15, 0);
        LocalDateTime end = LocalDateTime.of(2019, 11, 20, 19, 25, 30);
        Duration d = Duration.between(start, end);
        System.out.println(d);

        Period p = LocalDate.of(2020, 3, 17).until(LocalDate.of(2021, 8, 9));
        System.out.println(p);
    }

    @Test
    public void test16() {
        // 本月第一天0:00时刻:
        LocalDateTime firstDay = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        System.out.println(firstDay);

        // 本月最后1天:
        LocalDate lastDay = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        System.out.println(lastDay);

        // 下月第1天:
        LocalDate nextMonthFirstDay = LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth());
        System.out.println(nextMonthFirstDay);

        // 本月第1个周一:
        LocalDate firstWeekday = LocalDate.now().with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
        System.out.println(firstWeekday);
    }

    @Test
    public void test15() {
        LocalDateTime initDt = LocalDateTime.of(2020, 5, 12, 10, 10, 30, 653);
        LocalDateTime dt1 = initDt.plusDays(2);
        System.out.println(dt1);
        System.out.println(initDt);

        LocalDateTime dt2 = initDt.withDayOfMonth(5);
        System.out.println(dt2);
        System.out.println(initDt);
    }
}
