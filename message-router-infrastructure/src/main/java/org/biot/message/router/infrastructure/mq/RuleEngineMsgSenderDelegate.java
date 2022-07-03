package org.biot.message.router.infrastructure.mq;

import org.biot.message.router.driven.msg.RuleEngineMsgSender;
import org.biot.message.router.infrastructure.mq.rabbit.producer.RuleEnginProducer;
import org.biot.things.core.message.ThingsCoreMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RuleEngineMsgSenderDelegate implements RuleEngineMsgSender {
    // todo 通过工厂创建
    @Autowired
    private RuleEnginProducer producer;

    /**
     * 转发相关消息至规则引擎
     *
     * @param message
     */
    @Override
    public void forward(ThingsCoreMessage message) {
        producer.send(message);
    }
}
