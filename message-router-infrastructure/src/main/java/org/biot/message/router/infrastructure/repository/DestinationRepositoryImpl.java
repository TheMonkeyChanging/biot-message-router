package org.biot.message.router.infrastructure.repository;

import org.biot.message.router.domain.destination.Destination;
import org.biot.message.router.domain.destination.DestinationId;
import org.biot.message.router.domain.destination.DestinationRepository;
import org.biot.message.router.infrastructure.dal.mapper.DestinationMapper;
import org.biot.message.router.infrastructure.dal.model.DestinationDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DestinationRepositoryImpl implements DestinationRepository {
    @Autowired
    private DestinationMapper destinationMapper;

    /**
     * 根据destination ID 获取目的地信息
     *
     * @param id
     * @return
     */
    @Override
    public Destination of(DestinationId id) {
        DestinationDo destinationDo = destinationMapper.selectByUuid(id.getUuid());
        return AssembleMapper.INSTANCE.toDestination(destinationDo);
    }
}
