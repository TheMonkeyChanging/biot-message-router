package org.biot.message.router.infrastructure.dal.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.biot.message.router.infrastructure.dal.model.SubscribeDo;

import java.util.List;

@Mapper
public interface SubscribeMapper {
    List<SubscribeDo> selectByTenant(@Param("tenantId") String tenantId);
}
