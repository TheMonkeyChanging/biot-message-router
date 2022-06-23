package org.biot.message.router.domain.destination.client;

import org.biot.BiotResult;
import org.biot.things.core.message.ThingsCoreMessage;

public interface DestinationClient {
    /**
     * 转发things-core消息
     *
     * @param message
     * @return
     */
    BiotResult<Void> send(ThingsCoreMessage message);
}
