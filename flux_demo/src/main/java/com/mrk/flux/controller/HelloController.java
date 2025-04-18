package com.mrk.flux.controller;

import com.mrk.flux.model.Message;
import com.mrk.flux.model.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping(value = "/user", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> getMessages() {
        return Flux.just(
                new User("Hello"),
                new User("World!")
        ).delayElements(Duration.ofSeconds(1));
    }

    @GetMapping("/message")
    public Mono<Message> getMessage() {
        // 返回一个 Mono 包装的 Message 对象
        return Mono.just(new Message("Hello, this is a single JSON response!"));
    }
}