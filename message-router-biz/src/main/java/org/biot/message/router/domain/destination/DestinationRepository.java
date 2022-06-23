package org.biot.message.router.domain.destination;

public interface DestinationRepository {
    /**
     * 根据destination ID 获取目的地信息
     *
     * @param id
     * @return
     */
    Destination of(DestinationId id);
}
