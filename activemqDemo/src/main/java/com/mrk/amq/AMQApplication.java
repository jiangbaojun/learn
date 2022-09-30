package com.mrk.amq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

/**
 * activemq示例
 * @author baojun.jiang
 * @date 2022/9/27 17:43
 */
@SpringBootApplication
@EnableJms
public class AMQApplication {

    public static void main(String[] args) {
        SpringApplication.run(AMQApplication.class, args);
    }

}
