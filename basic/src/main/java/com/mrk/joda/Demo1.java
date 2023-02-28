package com.mrk.joda;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

public class Demo1 {

    @Test
    public void t4() {
        String dateStr = "2019-10-28 10:23:12";
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

        DateTime dateTime = fmt.parseDateTime(dateStr);
        System.out.println(dateTime);

        LocalDateTime now = LocalDateTime.now();
        String nowStr = fmt.print(now);
        System.out.println(nowStr);

        DateTimeFormatter fmt2 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withZone(DateTimeZone.forID("America/New_York"));
        DateTime dateTime2 = fmt2.parseDateTime("2019-6-28 10:23:12");
        System.out.println(dateTime2);
    }

    @Test
    public void t3() {
        DateTime today = new DateTime();
// 获取777秒之前的时间
        DateTime dateTime1 = today.minus(777 * 1000);
// 获取明天的时间
        DateTime tomorrow = today.plusDays(1);
// 获取当月第一天的日期
        DateTime dateTime2 = today.withDayOfMonth(1);
// 获取当前时间三个月后的月份的最后一天
        DateTime dateTime3 = today.plusMonths(3).dayOfMonth().withMaximumValue();

        System.out.println(dateTime1);
        System.out.println(tomorrow);
        System.out.println(dateTime3);
    }

    @Test
    public void t2() {
        // 1.使用系统时间
        DateTime dateTime1 = new DateTime();
// 2.使用jdk中的date
        Date jdkDate1 = new Date();
        DateTime dateTime2 = new DateTime(jdkDate1);
// 3.使用毫秒数指定
        Date jdkDate2 = new Date();
        long millis = jdkDate2.getTime();
        DateTime dateTime3 = new DateTime(millis);
// 4.使用Calendar
        Calendar calendar = Calendar.getInstance();
        DateTime dateTime4 = new DateTime(calendar);
// 5.使用多个字段指定一个瞬间时刻(局部时间片段)
// year  month day hour(midnight is zero) minute second milliseconds
        DateTime dateTime5 = new DateTime(2000, 1, 1, 0, 0, 0, 0);
// 6.由一个DateTime生成另一个DateTime
        DateTime dateTime6 = new DateTime(dateTime1);
// 7.有时间字符串生成DateTime
        String timeString = "2019-01-01T00:00:00-06:00";
        DateTime dateTime7 = DateTime.parse(timeString);
    }

    @Test
    public void t1() {
        //Date To Joda-Time
        Date date = new Date();
        DateTime dateTime = new DateTime(date);

        //Calendar To Joda-Time
        Calendar calendar2 = Calendar.getInstance();
        DateTime dateTime2 = new DateTime(calendar2);

        //Joda-Time To Date
        Date date3 = new Date();
        DateTime dateTime3 = new DateTime(date3);
        Date date3_1 = dateTime3.toDate();

        //Joda-Time To Calendar
        Calendar calendar4 = Calendar.getInstance();
        DateTime dateTime4 = new DateTime(calendar4);
        Calendar calendar4_1 = dateTime4.toCalendar(Locale.CHINA);
    }

    @Test
    public void test(){
        Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
        System.out.println("可用时区总数："+availableZoneIds.size());
        for(String zoneId : availableZoneIds){
            System.out.println(zoneId);
        }
    }
}
