package org.biot.message.router.infrastructure.mq.rabbit.producer;

import org.biot.things.core.message.ThingsCoreMessage;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 面向规则引擎的消息生产者
 */
@Component
public class RuleEnginProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange ruleEngineExchange;

    @Autowired
    private Binding ruleEngineBinding;

    /**
     * 发送消息
     *
     * @param message
     */
    public void send(ThingsCoreMessage message) {
        String exchange = ruleEngineExchange.getName();
        String routingKey = ruleEngineBinding.getRoutingKey();
        rabbitTemplate.convertAndSend(exchange, routingKey, message, new CorrelationData(UUID.randomUUID().toString()));
    }
}
