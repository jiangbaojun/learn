package com.example.demo.util;

import java.util.Random;

public class Tutil {
    public static String  toUpperCase(String s){
        return s.toUpperCase();
    }
    public static String getRandom(int len) {
        if(len<1){
            return "";
        }
        Random r = new Random();
        StringBuilder rs = new StringBuilder();
        int firstInt = r.nextInt(10);
        rs.append(firstInt==0?1:firstInt);
        for (int i = 0; i < len-1; i++) {
            rs.append(r.nextInt(10));
        }
        return rs.toString();
    }
}
