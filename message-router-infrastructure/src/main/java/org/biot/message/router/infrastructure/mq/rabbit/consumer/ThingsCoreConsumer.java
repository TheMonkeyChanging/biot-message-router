package org.biot.message.router.infrastructure.mq.rabbit.consumer;

import com.alibaba.fastjson.JSON;
import org.biot.BiotResult;
import org.biot.message.router.driving.msg.ThingsCoreMsgProcessor;
import org.biot.things.core.message.ThingsCoreMessage;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消费来自things-core的消息
 * todo 按租户区分，不同租户使用不同queue
 */
@Component
public class ThingsCoreConsumer {
    @Autowired
    private ThingsCoreMsgProcessor msgProcessor;

    @RabbitListener(queues = "defaultTenantQueue")
    @RabbitHandler
    public void process(ThingsCoreMessage message) {
        System.out.println(System.currentTimeMillis() + ": 消费者接受到的消息是：" + JSON.toJSONString(message));
        BiotResult rs = msgProcessor.processReceivedMessage(message);
        System.out.println(JSON.toJSONString(rs));
    }
}
