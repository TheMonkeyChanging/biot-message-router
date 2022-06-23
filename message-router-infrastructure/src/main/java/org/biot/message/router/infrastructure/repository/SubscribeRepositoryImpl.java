package org.biot.message.router.infrastructure.repository;

import org.biot.message.router.domain.subscribe.Subscribe;
import org.biot.message.router.domain.subscribe.SubscribeRepository;
import org.biot.message.router.infrastructure.dal.mapper.SubscribeMapper;
import org.biot.message.router.infrastructure.dal.model.SubscribeDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SubscribeRepositoryImpl implements SubscribeRepository {
    @Autowired
    private SubscribeMapper subscribeMapper;

    @Override
    public List<Subscribe> ofTenant(String tenantId) {
        List<Subscribe> rs = new ArrayList<>();
        List<SubscribeDo> subscribeDos = subscribeMapper.selectByTenant(tenantId);
        if (!CollectionUtils.isEmpty(subscribeDos)) {
            subscribeDos.forEach(s -> rs.add(AssembleMapper.INSTANCE.toSubscribe(s)));
        }

        return rs;
    }
}
