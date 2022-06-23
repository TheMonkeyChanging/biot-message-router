package org.biot.message.router.infrastructure.dal.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 路由目的地DO
 */
@Getter
@Setter
public class DestinationDo {
    private long id;
    private String tenantId;
    private String uuid;
    private String type;
    private String desc;
    private String config;
    private Date gmtCreate;
    private Date gmtModified;
}
