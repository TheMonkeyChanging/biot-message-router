package org.biot.message.router.domain.destination;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Destination {
    private DestinationId id;
    private DestinationType type;
    private String desc;
    private DestinationConfig config;
}
