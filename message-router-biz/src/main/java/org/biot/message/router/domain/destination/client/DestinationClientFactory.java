package org.biot.message.router.domain.destination.client;

import org.biot.BiotResult;
import org.biot.message.router.domain.destination.Destination;
import org.biot.message.router.domain.destination.DestinationId;
import org.biot.message.router.domain.destination.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 目的地client工厂
 */
@Component
public class DestinationClientFactory {
    @Autowired
    private DestinationRepository destinationRepository;

    @Autowired
    private DestinationClientBuilder clientBuilder;

    // todo 考虑刷新机制
    private Map<DestinationId, DestinationClient> clientMap = new ConcurrentHashMap<>();

    /**
     * 根据destination id获取对应的client
     *
     * @param destinationId
     * @return
     */
    public BiotResult<DestinationClient> of(DestinationId destinationId) {
        DestinationClient client = clientMap.get(destinationId);
        if (client == null) {
            // todo 并发机制
            Destination destination = destinationRepository.of(destinationId);
            if (destination == null) {
                // shouldn't happen
                return BiotResult.failResult(5001010, "No destination found!");
            } else {
                client = clientBuilder.build(destination);
                clientMap.put(destinationId, client);
            }
        }
        return BiotResult.successResult(client);
    }
}
