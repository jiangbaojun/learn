package com.mrk.flux.test;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

public class MonoMethodExamples {

    @Test
    public void test1(){
        // map(): 对数据进行转换
        Mono<String> monoMap = Mono.just("token")
                .map(token -> token.toUpperCase());
        monoMap.subscribe(val -> System.out.println("map: " + val));

        // flatMap(): 返回一个新的 Mono
        Mono<String> monoFlatMap = Mono.just("123")
                .flatMap(id -> Mono.just("user_" + id));
        monoFlatMap.subscribe(val -> System.out.println("flatMap: " + val));

        // filter(): 过滤不符合条件的值
        Mono<String> monoFilter = Mono.just("admin")
                .filter(role -> role.equals("admin"));
        monoFilter.subscribe(val -> System.out.println("filter: " + val),
                err -> {},
                () -> System.out.println("filter: completed (value may be filtered)"));

        // then(): 忽略上一个结果，执行下一个操作
        Mono<Void> monoThen = Mono.just("step1")
                .doOnNext(val -> System.out.println("then: first = " + val))
                .then(Mono.fromRunnable(() -> System.out.println("then: second step")));
        monoThen.subscribe();

        // block(): 阻塞式获取值（只用于非响应式上下文中）
        String result = Mono.just("blocking result").block();
        System.out.println("block: " + result);
    }

    @Test
    public void test2(){
        //doOnNext() 完全不影响数据流的结构或数据本身，它只是用来“旁观、打日志、记录副作用”，用完它之后你仍然可以继续使用 map()、flatMap() 等方法链式操作
        Mono.just("123")
                .doOnNext(val -> System.out.println("step1 原始值: " + val)) // 只是看一下
                .map(val -> "user_" + val)                                  // 转换值
                .doOnNext(val -> System.out.println("step2 转换后: " + val)) // 再看一下
                .flatMap(val -> Mono.just(val + "_token"))                  // 模拟异步操作
                .subscribe(System.out::println);                            // 最终消费

    }
}
