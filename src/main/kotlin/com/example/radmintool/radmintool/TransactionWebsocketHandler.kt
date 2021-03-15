package com.example.radmintool.transaction

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.atomic.AtomicLong

class RadmintoolUser(val id: Long, val name: String)
class Message(val msgType: String, val data: Any)

@Component
class TransactionWebsocketHandler : TextWebSocketHandler() {

    val sessionList = HashMap<WebSocketSession, RadmintoolUser>()
    var uids = AtomicLong(0)


    @Throws(Exception::class)
    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessionList.remove(session)
    }

    /**
     *  Takes in a websocket (the one that has send the message), and a message that they have sent.
     *  The first step is to turn this message into JSON, which we do on Line 28 using the Jackson ObjectMapper object and
     *  getting the message from the payload property of the passed in message.
     */
    public override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val json = ObjectMapper().readTree(message.payload)
        // {type: "join/say", data: "name/msg"}
        when (json.get("type").asText()) {
            "register" -> {
                val user = RadmintoolUser(uids.getAndIncrement(), json.get("data").asText())
                sessionList.put(session, user)
            }
//            "sendUpdateOfTransactions" -> {
//                broadcast(Message("sendUpdateOfTransactions", json.get("data").asText()))
//            }
        }
    }

    /**
     * This helper function (emit) simply takes the websocket we wish to send a message to, and the message we wish to send.
     * We call the sendMessage function on the websocket, construct a new TextMessage object, which is part of the Spring Websocket
     * implementation, and then using Jackson JSON we transform the message we wish to send into a JSON string.
     */
    fun emit(session: WebSocketSession, msg: Message) = session.sendMessage(TextMessage(jacksonObjectMapper().writeValueAsString(msg)))

    /**
     * This helper function (broadcast) simply takes the message we wish to send to all websockets, and iterates over each element of the
     * hashmap that contains all of the sessions using the forEach function, and then simply calls the emit function
     * we have just described passing in the session object from each iteration, and the passed in message.
     */
    fun broadcast(msg: Message) = sessionList.forEach { emit(it.key, msg) }

    /**
     *  This helper function (broadcastToOthers) is very similar to the broadcast helper function, except it specifically excludes a single
     *  passed in websocket. This is achieved by again using one of the functional utilities of the Kotlin collections by filtering the
     *  hashmap (to include every session except the one we wish to exclude) and then executing the forEach in the same way
     *  as the broadcast function previously described.
     */
    fun broadcastToOthers(me: WebSocketSession, msg: Message) = sessionList.filterNot { it.key == me }.forEach { emit(it.key, msg) }


    @Configuration
    @EnableWebSocket
    class WSConfig : WebSocketConfigurer {
        override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
            registry.addHandler(TransactionWebsocketHandler(), "/radmintoolWebsocket").withSockJS()
        }
    }
}
