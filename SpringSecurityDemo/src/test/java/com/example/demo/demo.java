package com.example.demo;

import com.alibaba.druid.support.json.JSONUtils;
import org.w3c.dom.ls.LSOutput;

public class demo {
    public static void main(String[] args) {
        int a = 1<<2;
        System.out.println(a);

        User u1 = new User("456");
        User u2 = new User("123");

        System.out.println(u1==u2);
        System.out.println(u1.equals(u2));
    }
}
