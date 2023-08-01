package com.ocean;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class MqProducerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MqProducerApplication.class, args);
    }
}
