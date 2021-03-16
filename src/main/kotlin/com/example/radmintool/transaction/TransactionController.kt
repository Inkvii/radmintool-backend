package com.example.radmintool.transaction

import com.example.radmintool.radmintool.RadmintoolService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal


data class CaptureRequestDTO(
    val amount: BigDecimal,
    val paidById: Long
)

@RestController
@RequestMapping("/transactions", consumes = ["application/json"])
class TransactionController(
    val transactionService: TransactionService,
    val radmintoolService: RadmintoolService
) {
    private val log = LoggerFactory.getLogger(javaClass)


    @PostMapping("/capture")
    fun addNewCapture(@RequestBody captureRequestDTO: CaptureRequestDTO): ResponseEntity<Boolean> {
        log.debug("> addNewCapture - amount: {}, paidById: {}", captureRequestDTO.amount, captureRequestDTO.paidById)
        val isTransactionCaptured: Boolean = transactionService.captureTransaction(captureRequestDTO.amount, captureRequestDTO.paidById)

        radmintoolService.notifyThatNewTransactionWasCaptured()
        log.debug("< addNewCapture is ok")
        return ResponseEntity.ok(isTransactionCaptured)
    }
}
