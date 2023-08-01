package com.ocean.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {
    public static final String EXCHANGE_NAME = "boot_topic_exchange";
    public static final String QUEUE_NAME = "boot_queue";

    // 1. 交换机
    @Bean("bootExchange")
    public Exchange bootExchange() {
        // 声明交换机名字，是否持久化
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).durable(true).build();
    }
    // 2. Queue队列
    @Bean("bootQueue")
    public Queue bootQueue() {
        return QueueBuilder.durable(QUEUE_NAME).build();
    }
    // 3. Binding---交换机和队列的绑定关系
    @Bean
    public Binding bootBinding(@Qualifier("bootQueue") Queue queue, @Qualifier("bootExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("boot.#").noargs();
    }
}
