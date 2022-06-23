package org.biot.message.router.domain.subscribe;

import lombok.NoArgsConstructor;
import org.biot.EntityId;

@NoArgsConstructor
public class SubscribeId extends EntityId<String> {
    public SubscribeId(String tenantId, String uuid) {
        super(tenantId, uuid);
    }
}
