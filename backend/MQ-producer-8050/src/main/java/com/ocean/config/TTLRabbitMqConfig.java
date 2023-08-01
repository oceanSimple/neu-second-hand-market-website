package com.ocean.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class TTLRabbitMqConfig {
    public static final String TTL_EXCHANGE_NAME = "ttl_exchange";
    public static final String TTL_QUEUE_NAME = "ttl_queue";

    @Bean
    public Exchange ttlExchange() {
        return ExchangeBuilder.directExchange(TTL_EXCHANGE_NAME).durable(true).build();
    }

    @Bean
    public org.springframework.amqp.core.Queue ttlQueue() {
        HashMap<String, Object> args = new HashMap<>();
        // 设置队列中的消息10min过期
        args.put("x-message-ttl", 600000);
        // 设置绑定交换机
        args.put("x-dead-letter-exchange", DeadQueueConfig.DEAD_EXCHANGE_NAME);
        // 设置绑定交换机的routingKey
        args.put("x-dead-letter-routing-key", "dead");
        return QueueBuilder.durable(TTL_QUEUE_NAME).withArguments(args).build();
    }

    @Bean
    public Binding ttlBinding() {
        return BindingBuilder.bind(ttlQueue()).to(ttlExchange()).with("ttl").noargs();
    }
}
