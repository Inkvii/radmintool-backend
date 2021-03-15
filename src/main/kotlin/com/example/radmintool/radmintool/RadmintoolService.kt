package com.example.radmintool.radmintool

import com.example.radmintool.transaction.Message
import com.example.radmintool.transaction.TransactionService
import com.example.radmintool.transaction.TransactionWebsocketHandler
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class RadmintoolService(
    val socketHandler: TransactionWebsocketHandler,
    val transactionService: TransactionService
) {
    private val log = LoggerFactory.getLogger(javaClass)

    fun notifyThatNewTransactionWasCaptured() {
        log.debug("> notifyThatNewTransactionWasCaptured")
        val transactionList = transactionService.getTransactionsInLast5Minutes()
        socketHandler.broadcast(Message("sendUpdateOfTransactions", jacksonObjectMapper().writeValueAsString(transactionList)))
        log.debug("< notifyThatNewTransactionWasCaptured")
    }
}
