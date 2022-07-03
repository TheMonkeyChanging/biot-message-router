package org.biot.message.router.domain.service;

import lombok.NonNull;
import org.biot.BiotResult;
import org.biot.message.BiotMessageType;
import org.biot.message.router.domain.destination.DestinationId;
import org.biot.message.router.domain.destination.client.DestinationClient;
import org.biot.message.router.domain.destination.client.DestinationClientFactory;
import org.biot.message.router.domain.router.Router;
import org.biot.message.router.domain.router.RouterFactory;
import org.biot.message.router.driven.msg.RuleEngineMsgSender;
import org.biot.things.core.message.ThingsCoreMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DispatchService {
    private static final Logger log = LoggerFactory.getLogger(DispatchService.class);

    @Autowired
    private RuleEngineMsgSender ruleEngineMsgSender;

    @Autowired
    private RouterFactory routerFactory;

    @Autowired
    private DestinationClientFactory clientFactory;

    /**
     * 分发消息
     *
     * @param tenantId
     * @param message
     */
    public void dispatch(@NonNull String tenantId, ThingsCoreMessage message) {
        sendToRuleEngine(message);

        /**
         * 1. 获取目的地列表
         * 2. 获取Client
         * 3. 转发消息
         */
        Router router = routerFactory.ofTenant(tenantId);
        Set<DestinationId> destinationIds =  router.findRoutes(message.getProductId(), message.getType());
        for (DestinationId id : destinationIds) {
            sendToDestination(id, message);
        }
    }

    /**
     * 消息发送至规则引擎
     *
     * @param message
     */
    private void sendToRuleEngine(ThingsCoreMessage message) {
        try {
            if (needForwardToRuleEngine(message.getType())) {
                System.out.println("========== forward msg to rule engine ===============");
                ruleEngineMsgSender.forward(message);
            }
        } catch (Exception e) {
            // todo 补偿机制
            log.error("Fail to send msg to rule engine: ", e);
        }
    }

    /**
     * 是否需要转发至规则引擎
     * todo 逻辑暂且放在这里，考虑放在哪里比较合适
     *
     * @param type
     * @return
     */
    private boolean needForwardToRuleEngine(BiotMessageType type) {
        switch (type) {
            case DEVICE_EVENT:
            case DEVICE_PROPERTY:
                return true;
            default:
                return false;
        }
    }

    /**
     * 发送消息至目的地
     *
     * @param id
     * @param message
     */
    private void sendToDestination(DestinationId id, ThingsCoreMessage message) {
        BiotResult<DestinationClient> clientBiotResult = clientFactory.of(id);
        if (clientBiotResult.isSuccess()) {
            DestinationClient client = clientBiotResult.getResult();
            try {
                client.send(message);
            } catch (Exception e) {
                log.error("Fail to send msg({}) to {}, because: ", message.getMsgId(), id, e.getMessage());
            }
        } else {
            log.error("Client of destination({}) not found!", id);
        }
    }
}
