package org.biot.message.router.infrastructure.repository;

import com.alibaba.fastjson.JSON;
import org.biot.message.router.domain.destination.Destination;
import org.biot.message.router.domain.destination.DestinationConfig;
import org.biot.message.router.domain.subscribe.Subscribe;
import org.biot.message.router.infrastructure.dal.model.DestinationDo;
import org.biot.message.router.infrastructure.dal.model.SubscribeDo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Entity与DO的转换类
 */
@Mapper
public interface AssembleMapper {
    AssembleMapper INSTANCE = Mappers.getMapper(AssembleMapper.class);

    @Mapping(source = "tenantId", target = "id.tenantId")
    @Mapping(source = "uuid", target = "id.uuid")
    @Mapping(source = "productIds", target = "productIdsByString")
    @Mapping(source = "messageTypes", target = "messageTypesByString")
    @Mapping(target = "productIds", ignore = true)
    @Mapping(target = "messageTypes", ignore = true)
    Subscribe toSubscribe(SubscribeDo subscribeDo);

    @Mapping(source = "id.tenantId", target = "tenantId")
    @Mapping(source = "id.uuid", target = "uuid")
    @Mapping(source = "productIdsFormatString", target = "productIds")
    @Mapping(source = "messageTypesFormatString", target = "messageTypes")
    @Mapping(target = "id", ignore = true)
    SubscribeDo toSubscribeDo(Subscribe subscribe);

    @Mapping(source = "tenantId", target = "id.tenantId")
    @Mapping(source = "uuid", target = "id.uuid")
    Destination toDestination(DestinationDo destinationDo);

    @Mapping(source = "id.tenantId", target = "tenantId")
    @Mapping(source = "id.uuid", target = "uuid")
    @Mapping(target = "id", ignore = true)
    DestinationDo toDestinationDo(Destination destination);

    default DestinationConfig map(String value) {
        return JSON.parseObject(value, DestinationConfig.class);
    }

    default String map(DestinationConfig value) {
        return JSON.toJSONString(value);
    }
}
