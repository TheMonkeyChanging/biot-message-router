package org.biot.message.router.infrastructure.mq.rabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ 相关配置
 */
@Configuration
public class RabbitConfig {
    @Bean
    public DirectExchange ruleEngineExchange() {
        return new DirectExchange("ruleEngineExchange", true, false);
    }

    @Bean
    public Queue ruleEngineQueue() {
        return new Queue("ruleEngineQueue", true, false, false);
    }

    @Bean
    public Binding ruleEngineBinding() {
        return BindingBuilder.bind(ruleEngineQueue()).to(ruleEngineExchange()).with("tenant.0123456789abcdef");
    }

//    @Bean
//    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
//        return new Jackson2JsonMessageConverter(objectMapper);
//    }
}
