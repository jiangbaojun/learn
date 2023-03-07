package com.mrk.timezone;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Demo5 {

    @Test
    public void t1(){
        TimeZone newYorkTZ = TimeZone.getTimeZone("America/New_York");
        TimeZone defaultTZ = TimeZone.getDefault();
        Calendar c1 = new GregorianCalendar(newYorkTZ);
        Calendar c2 = new GregorianCalendar(defaultTZ);
        System.out.println(c1.getTime());
        System.out.println(c2.getTime());

        outTime(c1);
        outTime(c2);
    }

    public void outTime(Calendar cal){
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        System.out.println(year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second);
    }
}
