package com.example.radmintool.transaction

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.stereotype.Controller
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

/**
 * Websocket controller listens to requests and broadcasts it to all subscribers
 */
@Controller
class TransactionWebsocketController {
    private val log = LoggerFactory.getLogger(javaClass)

    @MessageMapping("/test")
    @SendTo("/topic/test")
    fun testSendingMessage(message: String): String {
        log.info("Somebody is sending test message of " + message)
        return "Hello to you too!"
    }
}

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig : WebSocketMessageBrokerConfigurer {
    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.enableSimpleBroker("/topic")
        registry.setApplicationDestinationPrefixes("/app")
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/radmintoolWebsocket").setAllowedOriginPatterns("*").withSockJS()
    }
}
