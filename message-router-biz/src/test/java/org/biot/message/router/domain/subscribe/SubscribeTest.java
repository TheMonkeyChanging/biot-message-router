package org.biot.message.router.domain.subscribe;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubscribeTest {
    @Test
    void testProductTransformMethod() {
        Subscribe subscribe = new Subscribe();
        subscribe.setProductIdsByString("1,2,3,a, b, c");
        List<String> productIds = subscribe.getProductIds();
        assertEquals(productIds.size(), 6);
        assertEquals(productIds.get(4), "b");

        subscribe.setProductIdsByString("");
        productIds = subscribe.getProductIds();
        assertEquals(productIds.size(), 0);
    }

    @Test
    void testMessageTransformMethod() {
        Subscribe subscribe = new Subscribe();
        subscribe.setMessageTypesByString("1,2,3,a, b, c");
        List<String> types = subscribe.getMessageTypes();
        assertEquals(types.size(), 6);
        assertEquals(types.get(4), "b");

        subscribe.setMessageTypesByString("");
        types = subscribe.getMessageTypes();
        assertEquals(types.size(), 0);
    }
}
