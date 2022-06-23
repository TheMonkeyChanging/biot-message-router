package org.biot.message.router.domain.subscribe;

import java.util.List;

/**
 * 订阅信息资源库
 */
public interface SubscribeRepository {
    /**
     * 获取租户的订阅列表
     *
     * @param tenantId
     * @return
     */
    List<Subscribe> ofTenant(String tenantId);
}
