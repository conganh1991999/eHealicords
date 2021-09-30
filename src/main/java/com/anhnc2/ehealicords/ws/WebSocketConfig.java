package com.anhnc2.ehealicords.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("*");
    }
}

@Controller
class WsPingController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WsPingController.class);

    @MessageMapping("/ping")
    @SendTo("/topic/pong")
    public String handle(String message) {
        LOGGER.info("Handle websocket ping with message = {}", message);
        return "PONG";
    }
}
