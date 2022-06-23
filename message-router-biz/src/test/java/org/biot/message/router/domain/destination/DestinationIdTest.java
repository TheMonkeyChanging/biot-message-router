package org.biot.message.router.domain.destination;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DestinationIdTest {

    @Test
    void testEqualAndHash() {
        DestinationId d1 = new DestinationId("123", "abc");
        DestinationId d2 = new DestinationId("123", "abc");
        assertEquals(d1, d2);
        assertEquals(d1.hashCode(), d2.hashCode());

        DestinationId d3 = new DestinationId("123456", "abc");
        assertFalse(d1.equals(d3));
    }
}
