package org.biot.message.router.infrastructure.mq.rabbit.producer;

import com.alibaba.fastjson.JSON;
import org.biot.BiotResult;
import org.biot.message.router.domain.destination.client.DestinationClient;
import org.biot.things.core.message.ThingsCoreMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

// todo 暂且这样写，实际应根据Destination信息创建
@Component
public class RabbitClient implements DestinationClient {
    private static final Logger log = LoggerFactory.getLogger(RabbitClient.class);

    /**
     * 转发things-core消息
     *
     * @param message
     * @return
     */
    @Override
    public BiotResult<Void> send(ThingsCoreMessage message) {
        log.info("Forward message: {}", JSON.toJSONString(message));
        return BiotResult.successResult(Void.TYPE);
    }
}
