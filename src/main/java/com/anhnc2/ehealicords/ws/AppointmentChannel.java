package com.anhnc2.ehealicords.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
public class AppointmentChannel {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentChannel.class);

    @MessageMapping("/signaling/{id}")
    @SendTo("/topic/signaling/{id}")
    public Map<String, Object> handle(
            @PathVariable @DestinationVariable String id, @Payload Map<String, Object> data) {
        LOGGER.info("Receive message from channel signaling-{}: type={}", id, data.get("type"));
        return data;
    }
}
