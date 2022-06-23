package org.biot.message.router.infrastructure.mq;

import org.biot.message.router.domain.destination.Destination;
import org.biot.message.router.domain.destination.client.DestinationClient;
import org.biot.message.router.domain.destination.client.DestinationClientBuilder;
import org.biot.message.router.infrastructure.mq.rabbit.producer.RabbitClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * DestinationClientBuilder实现类，目前仅支持MQ类型。后续如增加其他类型，使用工厂类替换
 */
@Component
public class MqClientBuilder implements DestinationClientBuilder {
    @Autowired
    private RabbitClient rabbitClient;

    /**
     * 根据目的地信息构造DestinationClient
     *
     * @param destination
     * @return
     */
    @Override
    public DestinationClient build(Destination destination) {
        return rabbitClient;
    }
}
