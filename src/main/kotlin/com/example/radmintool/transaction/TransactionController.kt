package com.example.radmintool.transaction

import com.example.radmintool.radmintool.RadmintoolService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@RequestMapping("/transactions")
class TransactionController(
    val transactionService: TransactionService,
    val radmintoolService: RadmintoolService
) {
    private val log = LoggerFactory.getLogger(javaClass)


    @PostMapping("/capture")
    fun addNewCapture(amount: BigDecimal, paidById: Long): ResponseEntity<Boolean> {
        log.debug("> addNewCapture - amount: {}, paidById: {}", amount, paidById)
        val isTransactionCaptured: Boolean = transactionService.captureTransaction(amount, paidById)

        radmintoolService.notifyThatNewTransactionWasCaptured()
        log.debug("< addNewCapture is ok")
        return ResponseEntity.ok(isTransactionCaptured)
    }
}
