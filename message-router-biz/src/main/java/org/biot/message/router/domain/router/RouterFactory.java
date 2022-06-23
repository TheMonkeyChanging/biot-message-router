package org.biot.message.router.domain.router;

import org.biot.message.router.domain.subscribe.Subscribe;
import org.biot.message.router.domain.subscribe.SubscribeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 路由工厂
 */
@Component
public class RouterFactory {
    private static final Logger log = LoggerFactory.getLogger(RouterFactory.class);

    /**
     * 刷新路由表的周期
     */
    private static final long REFRESH_PERIOD = 90 * 1000L;

    @Autowired
    private SubscribeRepository subscribeRepository;

    /**
     * 路由表，key为tenantId
     */
    private Map<String, Router> routerMap = new ConcurrentHashMap<>();

    @PostConstruct
    void startRefreshThread() {
        new RefreshThread().start();
    }

    /**
     * 获取租户路由表
     *
     * @param tenantId
     * @return
     */
    public Router ofTenant(String tenantId) {
        Router router = routerMap.get(tenantId);
        if (router == null) {
            synchronized (this) {
                router = routerMap.get(tenantId);
                if (router == null) {
                    router = create(tenantId);
                }
            }
        }
        return router;
    }

    /**
     * 构建租户路由表，并放入map
     *
     * @param tenantId
     * @return
     */
    private Router create(String tenantId) {
        Router router = new Router(tenantId);
        List<Subscribe> subscribeList = subscribeRepository.ofTenant(tenantId);
        router.buildRouters(subscribeList);
        routerMap.put(tenantId, router);
        return router;
    }

    /**
     * 刷新租户路由表
     *
     * @param tenantId
     */
    private void refresh(String tenantId) {
        Router router = routerMap.get(tenantId);
        List<Subscribe> subscribeList = subscribeRepository.ofTenant(tenantId);
        router.buildRouters(subscribeList);
    }

    /**
     * 定时刷新路由表线程
     */
    private class RefreshThread extends Thread {
        @Override
        public void run() {
            while (true) {
                for (String tenantId : routerMap.keySet()) {
                    try {
                        refresh(tenantId);
                    } catch (Exception e) {
                        log.warn("Refresh route of " + tenantId + " failed: ", e);
                    }
                }

                try {
                    Thread.sleep(REFRESH_PERIOD);
                } catch (InterruptedException e) {
                    // do nothing
                }
            }
        }
    }
}
