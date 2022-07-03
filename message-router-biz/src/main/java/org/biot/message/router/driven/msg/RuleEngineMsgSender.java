package org.biot.message.router.driven.msg;

import org.biot.things.core.message.ThingsCoreMessage;

/**
 * 向规则引擎发送消息
 */
public interface RuleEngineMsgSender {
    /**
     * 转发相关消息至规则引擎
     *
     * @param message
     */
    void forward(ThingsCoreMessage message);
}
