package org.biot.message.router.infrastructure.dal.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SubscribeDo {
    private long id;
    private String tenantId;
    private String uuid;
    private String productIds;
    private String messageTypes;
    private String desc;
    private String destinationId;
    private Date gmtCreate;
    private Date gmtModified;

}
