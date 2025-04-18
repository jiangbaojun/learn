package com.mrk.flux.test;

import reactor.core.publisher.Mono;

public class MonoCreateExamples {

    public static void main(String[] args) {

        // Mono.just(value): 用于已有值的情况
        Mono<String> monoJust = Mono.just("Hello, World!");
        monoJust.subscribe(value -> System.out.println("Mono.just: " + value));

        // Mono.empty(): 表示没有数据
        Mono<String> monoEmpty = Mono.empty();
        monoEmpty.subscribe(value -> System.out.println("Mono.empty: " + value),
                             error -> System.out.println("Error: " + error),
                             () -> System.out.println("Mono.empty: No value"));

        // Mono.error(e): 返回错误
        Mono<String> monoError = Mono.error(new RuntimeException("Something went wrong"));
        monoError.subscribe(value -> System.out.println("Mono.error: " + value),
                             error -> System.out.println("Error: " + error.getMessage()));

        // Mono.fromCallable(() -> value): 包装阻塞代码
        Mono<String> monoFromCallable = Mono.fromCallable(() -> {
            // 模拟阻塞操作
            Thread.sleep(500);
            return "From Callable";
        });
        monoFromCallable.subscribe(value -> System.out.println("Mono.fromCallable: " + value));

        // Mono.fromFuture(future): 包装 CompletableFuture
        Mono<String> monoFromFuture = Mono.fromFuture(() -> {
            return java.util.concurrent.CompletableFuture.supplyAsync(() -> "From Future");
        });
        monoFromFuture.subscribe(value -> System.out.println("Mono.fromFuture: " + value));

        // Mono.defer(() -> Mono.just(value)): 惰性创建
        Mono<String> monoDefer = Mono.defer(() -> Mono.just("Deferred Value"));
        monoDefer.subscribe(value -> System.out.println("Mono.defer: " + value));

        // Mono.create(sink -> {...}): 手动触发 sink
        Mono<String> monoCreate = Mono.create(sink -> {
            new Thread(() -> {
                sink.success("Hello from create!");
            }).start();
        });
        monoCreate.subscribe(value -> System.out.println("Mono.create: " + value));
    }
}
