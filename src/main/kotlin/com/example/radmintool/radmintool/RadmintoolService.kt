package com.example.radmintool.radmintool

import com.example.radmintool.transaction.TransactionService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service

/**
 * This is server side controller. By calling simpMessagingTemplate, message is sent to destination. Spring handles this in background.
 */
@Service
class RadmintoolService(
    val transactionService: TransactionService,
    val simpMessagingTemplate: SimpMessagingTemplate
) {
    private val log = LoggerFactory.getLogger(javaClass)

    fun notifyThatNewTransactionWasCaptured() {
        log.debug("> notifyThatNewTransactionWasCaptured")
        val transactionList = transactionService.getTransactionsInLast5Minutes()

        val sum = transactionList.sumOf { transaction -> transaction.amount }

        simpMessagingTemplate.convertAndSend("/topic/money", jacksonObjectMapper().writeValueAsString(sum))
        log.debug("< notifyThatNewTransactionWasCaptured")
    }
}
