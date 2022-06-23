package org.biot.message.router.domain.router;

import lombok.NonNull;
import org.biot.message.BiotMessageType;
import org.biot.message.router.domain.destination.DestinationId;
import org.biot.message.router.domain.subscribe.Subscribe;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 路由表，每个租户一张路由表
 */
public class Router {
    private final String tenantId;
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private volatile Map<Rule, Set<DestinationId>> routerMap = new HashMap<>();

    Router(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * 查找路由信息
     *
     * @param productId
     * @param type
     * @return
     */
    public Set<DestinationId> findRoutes(@NonNull String productId, @NonNull BiotMessageType type) {
        Set<DestinationId> rs = new HashSet<>();
        try {
            lock.readLock().lock();
            rs.addAll(ofAll());
            rs.addAll(ofAllProduct(type));
            rs.addAll(ofAllType(productId));
            rs.addAll(of(productId, type));
        } finally {
            lock.readLock().unlock();
        }
        return rs;
    }

    /**
     * 构建路由
     *
     * @param subscribeList
     */
    void buildRouters(List<Subscribe> subscribeList) {
        Map<Rule, Set<DestinationId>> map = new HashMap<>();
        if (!CollectionUtils.isEmpty(subscribeList)) {
            subscribeList.forEach(s -> parseRouter(s, map));
        }

        lock.writeLock().lock();
        routerMap = map;
        lock.writeLock().unlock();
    }

    /**
     * 根据订阅信息解析路由
     *
     * @param subscribe
     * @param map
     */
    private void parseRouter(Subscribe subscribe, Map<Rule, Set<DestinationId>> map) {
        for (String productId : subscribe.getProductIds()) {
            for (String type : subscribe.getMessageTypes()) {
                Rule r = new Rule(productId, type);
                Set<DestinationId> destinationIds = map.get(r);
                if (destinationIds == null) {
                    destinationIds = new HashSet<>();
                    map.put(r, destinationIds);
                }
                DestinationId id = new DestinationId(subscribe.getId().getTenantId(), subscribe.getDestinationId());
                destinationIds.add(id);
            }
        }
    }

    /**
     * 查找产品类型为所有、消息类型为所有的路由
     *
     * @return
     */
    private Set<DestinationId> ofAll() {
        Rule rule = new Rule(Subscribe.ALL_PRODUCT, Subscribe.ALL_TYPE);
        Set<DestinationId> rs = routerMap.get(rule);
        return rs != null ? rs : Collections.emptySet();
    }

    /**
     * 查找所有产品类型、特定消息类型的路由
     *
     * @param type
     * @return
     */
    private Set<DestinationId> ofAllProduct(BiotMessageType type) {
        Rule rule = new Rule(Subscribe.ALL_PRODUCT, type);
        Set<DestinationId> rs = routerMap.get(rule);
        return rs != null ? rs : Collections.emptySet();
    }

    /**
     * 查找所有消息类型、特定产品类型的路由
     *
     * @param productId
     * @return
     */
    private Set<DestinationId> ofAllType(String productId) {
        Rule rule = new Rule(productId, Subscribe.ALL_TYPE);
        Set<DestinationId> rs = routerMap.get(rule);
        return rs != null ? rs : Collections.emptySet();
    }

    /**
     * 查找特定产品、特定消息类型的路由
     *
     * @param productId
     * @param type
     * @return
     */
    private Set<DestinationId> of(String productId, BiotMessageType type) {
        Rule rule = new Rule(productId, type);
        Set<DestinationId> rs = routerMap.get(rule);
        return rs != null ? rs : Collections.emptySet();
    }
}
