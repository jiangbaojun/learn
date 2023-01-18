package com.example.thread.qlexpress;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;

public class Demo1 {

    public static void main(String[] args) throws Exception {
        Demo1 demo1 = new Demo1();
        demo1.t2();
    }

    private void t2() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        context.put("a", 1);
        context.put("b", 2);
        context.put("c", 3);
        String express = "c>1 && (a>0 || b>1) && (a+b)>8";
        Object r = runner.execute(express, context, null, true, false);
        System.out.println(r);
    }
    private void t1() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        context.put("a", 1);
        context.put("b", 2);
        context.put("c", 3);
        String express = "a + b * c";
        Object r = runner.execute(express, context, null, true, false);
        System.out.println(r);
    }
}
