package org.biot.message.router.domain.destination.client;

import org.biot.message.router.domain.destination.Destination;

/**
 * Destination client构造器
 */
public interface DestinationClientBuilder {
    /**
     * 根据目的地信息构造DestinationClient
     *
     * @param destination
     * @return
     */
    DestinationClient build(Destination destination);
}
