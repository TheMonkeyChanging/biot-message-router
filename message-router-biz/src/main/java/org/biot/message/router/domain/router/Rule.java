package org.biot.message.router.domain.router;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.biot.message.BiotMessageType;

/**
 * 消息分发路由规则
 */
@Data
@AllArgsConstructor
public class Rule {
    private String productId;
    private String messageType;

    public Rule(@NonNull String productId, @NonNull BiotMessageType messageType) {
        this.productId = productId;
        this.messageType = messageType.name();
    }
}
