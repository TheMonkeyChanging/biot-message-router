package org.biot.message.router.domain.router;

import org.assertj.core.util.Lists;
import org.biot.message.BiotMessageType;
import org.biot.message.router.domain.destination.DestinationId;
import org.biot.message.router.domain.subscribe.Subscribe;
import org.biot.message.router.domain.subscribe.SubscribeId;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RouterTest {
    private static final String TENANT_ID = "0123456789abcdef";

    @Test
    void testBuildOnlyAll() {
        Router router = new Router(TENANT_ID);
        Subscribe s1 = new Subscribe();
        s1.setId(new SubscribeId(TENANT_ID, "sub123"));
        s1.setDestinationId("dst123");
        s1.setProductIdsByString(Subscribe.ALL_PRODUCT);
        s1.setMessageTypesByString(Subscribe.ALL_TYPE);
        router.buildRouters(Lists.newArrayList(s1));

        Set<DestinationId> ids = router.findRoutes("p1", BiotMessageType.DEVICE_CREATE);
        assertEquals(ids.size(), 1);
        DestinationId d1 = (DestinationId) ids.toArray()[0];
        assertEquals(d1.getUuid(), "dst123");
    }

    @Test
    void testBuildAllProduct() {
        Router router = new Router(TENANT_ID);
        Subscribe s1 = new Subscribe();
        s1.setId(new SubscribeId(TENANT_ID, "sub123"));
        s1.setDestinationId("dst123");
        s1.setProductIdsByString(Subscribe.ALL_PRODUCT);
        String types = BiotMessageType.DEVICE_CREATE + "," + BiotMessageType.DEVICE_PROPERTY;
        s1.setMessageTypesByString(types);
        router.buildRouters(Lists.newArrayList(s1));

        Set<DestinationId> ids = router.findRoutes("p1", BiotMessageType.DEVICE_CREATE);
        assertEquals(ids.size(), 1);
        DestinationId d1 = (DestinationId) ids.toArray()[0];
        assertEquals(d1.getUuid(), "dst123");

        ids = router.findRoutes("p2", BiotMessageType.DEVICE_PROPERTY);
        assertEquals(ids.size(), 1);
        d1 = (DestinationId) ids.toArray()[0];
        assertEquals(d1.getUuid(), "dst123");

        ids = router.findRoutes("p2", BiotMessageType.DEVICE_EVENT);
        assertEquals(ids.size(), 0);
    }

    @Test
    void testBuildAllType() {
        Router router = new Router(TENANT_ID);
        Subscribe s1 = new Subscribe();
        s1.setId(new SubscribeId(TENANT_ID, "sub123"));
        s1.setDestinationId("dst123");
        s1.setProductIdsByString("p1,p2,");
        s1.setMessageTypesByString(Subscribe.ALL_TYPE);
        router.buildRouters(Lists.newArrayList(s1));

        Set<DestinationId> ids = router.findRoutes("p1", BiotMessageType.DEVICE_CREATE);
        assertEquals(ids.size(), 1);
        DestinationId d1 = (DestinationId) ids.toArray()[0];
        assertEquals(d1.getUuid(), "dst123");

        ids = router.findRoutes("p2", BiotMessageType.DEVICE_PROPERTY);
        assertEquals(ids.size(), 1);
        d1 = (DestinationId) ids.toArray()[0];
        assertEquals(d1.getUuid(), "dst123");

        ids = router.findRoutes("p3", BiotMessageType.DEVICE_EVENT);
        assertEquals(ids.size(), 0);
    }

    @Test
    void testBuildNoAll() {
        Router router = new Router(TENANT_ID);
        Subscribe s1 = new Subscribe();
        s1.setId(new SubscribeId(TENANT_ID, "sub123"));
        s1.setDestinationId("dst123");
        s1.setProductIdsByString("p1,p2,");
        String types = BiotMessageType.DEVICE_CREATE + "," + BiotMessageType.DEVICE_PROPERTY;
        s1.setMessageTypesByString(types);
        router.buildRouters(Lists.newArrayList(s1));

        // test p1 with type
        Set<DestinationId> ids = router.findRoutes("p1", BiotMessageType.DEVICE_CREATE);
        assertEquals(ids.size(), 1);
        DestinationId d1 = (DestinationId) ids.toArray()[0];
        assertEquals(d1.getUuid(), "dst123");
        ids = router.findRoutes("p1", BiotMessageType.DEVICE_PROPERTY);
        assertEquals(ids.size(), 1);
        d1 = (DestinationId) ids.toArray()[0];
        assertEquals(d1.getUuid(), "dst123");

        // test p2 with type
        ids = router.findRoutes("p2", BiotMessageType.DEVICE_CREATE);
        assertEquals(ids.size(), 1);
        d1 = (DestinationId) ids.toArray()[0];
        assertEquals(d1.getUuid(), "dst123");
        ids = router.findRoutes("p2", BiotMessageType.DEVICE_PROPERTY);
        assertEquals(ids.size(), 1);
        d1 = (DestinationId) ids.toArray()[0];
        assertEquals(d1.getUuid(), "dst123");

        // test not match case
        ids = router.findRoutes("p3", BiotMessageType.DEVICE_CREATE);
        assertEquals(ids.size(), 0);
        ids = router.findRoutes("p3", BiotMessageType.DEVICE_PROPERTY);
        assertEquals(ids.size(), 0);
        ids = router.findRoutes("p1", BiotMessageType.PRODUCT_CREATE);
        assertEquals(ids.size(), 0);
        ids = router.findRoutes("p2", BiotMessageType.PRODUCT_ENABLE);
        assertEquals(ids.size(), 0);
    }

    @Test
    void testBuildMix() {
        Router router = new Router(TENANT_ID);
        Subscribe s1 = new Subscribe();
        s1.setId(new SubscribeId(TENANT_ID, "sub123"));
        s1.setDestinationId("dst123");
        s1.setProductIdsByString("p1,p2,");
        s1.setMessageTypesByString(Subscribe.ALL_TYPE);
        Subscribe s2 = new Subscribe();
        s2.setId(new SubscribeId(TENANT_ID, "sub234"));
        s2.setDestinationId("dst234");
        s2.setProductIdsByString("p2,p3");
        String types = BiotMessageType.DEVICE_CREATE + "," + BiotMessageType.DEVICE_PROPERTY;
        s2.setMessageTypesByString(types);
        router.buildRouters(Lists.newArrayList(s1, s2));

        // test p1
        Set<DestinationId> ids = router.findRoutes("p1", BiotMessageType.PRODUCT_ENABLE);
        assertEquals(ids.size(), 1);
        DestinationId d1 = (DestinationId) ids.toArray()[0];
        assertEquals(d1.getUuid(), "dst123");

        // test p2
        ids = router.findRoutes("p2", BiotMessageType.DEVICE_OFFLINE);
        assertEquals(ids.size(), 1);
        d1 = (DestinationId) ids.toArray()[0];
        assertEquals(d1.getUuid(), "dst123");
        // 命中多条路由
        ids = router.findRoutes("p2", BiotMessageType.DEVICE_PROPERTY);
        assertEquals(ids.size(), 2);

        // test p3
        ids = router.findRoutes("p3", BiotMessageType.DEVICE_CREATE);
        assertEquals(ids.size(), 1);
        ids = router.findRoutes("p3", BiotMessageType.DEVICE_EVENT);
        assertEquals(ids.size(), 0);

        // test no product
        ids = router.findRoutes("p4", BiotMessageType.DEVICE_CREATE);
        assertEquals(ids.size(), 0);
    }
}
