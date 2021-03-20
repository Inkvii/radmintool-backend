package com.example.radmintool.transaction

import com.example.radmintool.radmintool.RadmintoolService
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.messaging.support.GenericMessage
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import org.springframework.web.socket.messaging.SessionSubscribeEvent

/**
 * Websocket controller listens to requests and broadcasts it to all subscribers
 */
@Controller
class TransactionWebsocketController(
    val radmintoolService: RadmintoolService
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @MessageMapping("/test")
    @SendTo("/topic/test")
    fun testSendingMessage(message: String): String {
        log.info("Somebody is sending test message of " + message)
        return "Hello to you too!"
    }

    @MessageMapping("/money")
    @SendTo("/topic/money")
    fun requestMoneyCounterUpdate() {
        log.info("Somebody is requesting update on money coutner")
        radmintoolService.notifyThatNewTransactionWasCaptured()
    }


}

@Component
class WebsocketMonitoring {
    private val log = LoggerFactory.getLogger(javaClass)


    @EventListener
    fun handleSessionSubscribeEvent(event: SessionSubscribeEvent) {
        val message = event.message as GenericMessage
        val destination = message.headers.get("simpDestination")
        val messageType = message.headers.get("simpMessageType")
        val sessionId = message.headers.get("simpSessionId")
        log.debug(
            """
            Message type: $messageType 
            Destination: $destination
            Session id: $sessionId
        """.trimIndent()
        )
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
