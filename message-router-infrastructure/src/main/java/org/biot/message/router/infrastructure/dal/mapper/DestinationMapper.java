package org.biot.message.router.infrastructure.dal.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.biot.message.router.infrastructure.dal.model.DestinationDo;

@Mapper
public interface DestinationMapper {
    DestinationDo selectByUuid(@Param("uuid") String uuid);
}
