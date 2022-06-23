package org.biot.message.router.domain.destination;

import lombok.Getter;

public enum DestinationType {
    MQ_RABBIT("RabbitMQ"),
    MQ_ROCKET("RabbitMQ"),
    MQ_KAFKA("Kafka"),
    // HTTP("HTTP"), // 暂不支持
    ;

    @Getter
    private final String desc;

    private DestinationType(String desc) {
        this.desc = desc;
    }
}
