package com.mrk.joda;


import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.junit.jupiter.api.Test;

public class Demo2 {

    @Test
    public void t2() {
        DateTimeZone newYorkTz = DateTimeZone.forID("America/New_York");
        DateTimeZone tokyoTz = DateTimeZone.forID("Asia/Tokyo");
        DateTime dateTime = new DateTime(2022,5,8,15,30,20);

        DateTime newYorkDt = dateTime.withZone(newYorkTz);
        DateTime tokyoDt = newYorkDt.withZone(tokyoTz);
        System.out.println(dateTime);
        System.out.println(newYorkDt);
        System.out.println(tokyoDt);
    }

    @Test
    public void t1() {
        DateTime dateTime = new DateTime();
        DateTime dateTimeUTC = new DateTime(DateTimeZone.UTC);
        System.out.println(dateTime);
        System.out.println(dateTimeUTC);

        System.out.println(dateTime.toDate());
        System.out.println(dateTimeUTC.toDate());

        System.out.println(dateTime.toLocalDateTime().toDate());
        System.out.println(dateTimeUTC.toLocalDateTime().toDate());

        System.out.println(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").print(dateTime));
        System.out.println(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").print(dateTimeUTC));

        System.out.println(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withZone(DateTimeZone.UTC).print(dateTime));
        System.out.println(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withZone(DateTimeZone.UTC).print(dateTimeUTC));
    }
}
