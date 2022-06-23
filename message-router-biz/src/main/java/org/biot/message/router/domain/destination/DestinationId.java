package org.biot.message.router.domain.destination;

import lombok.NoArgsConstructor;
import org.biot.EntityId;

@NoArgsConstructor
public class DestinationId extends EntityId<String> {
    public DestinationId(String tenantId, String destinationId) {
        super(tenantId, destinationId);
    }
}
