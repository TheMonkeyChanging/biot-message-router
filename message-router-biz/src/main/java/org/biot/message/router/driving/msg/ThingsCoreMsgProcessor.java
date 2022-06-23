package org.biot.message.router.driving.msg;

import org.biot.BiotResult;
import org.biot.BizError;
import org.biot.message.router.domain.service.DispatchService;
import org.biot.things.core.message.ThingsCoreMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ThingsCoreMsgProcessor {
    private static Logger log = LoggerFactory.getLogger(ThingsCoreMsgProcessor.class);

    @Autowired
    private DispatchService dispatchService;

    public BiotResult<Void> processReceivedMessage(ThingsCoreMessage message) {
        try {
            dispatchService.dispatch("0123456789abcdef", message);
            return BiotResult.successResult(Void.TYPE);
        } catch (Exception e) {
            // todo 业务侧重试
            log.warn("Dispatch msg exception: ", e);
            return BiotResult.failResult(BizError.BIZ_500.getErrorCode(), e.getMessage());
        }
    }
}
