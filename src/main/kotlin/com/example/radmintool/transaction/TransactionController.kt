package com.example.radmintool.transaction

import com.example.radmintool.radmintool.RadmintoolService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal


data class CaptureRequestDTO(
        val amount: BigDecimal,
        val paidById: Long
)

data class LongTransactionRequestDTO(
        val filter: TransactionFilterDTO = TransactionFilterDTO(),
        val pageIndex: Int
)

data class FilterTransaction(
        val filter: TransactionFilterDTO = TransactionFilterDTO(),
        val showOnFilterDemandOnly: Boolean = false
)

data class LongTransactionResponseDTO(
        val data: List<Transaction>,
        val totalCount: Long,
        val totalPages: Int,
)

data class TransactionFilterDTO(
    val fromId: Long? = null,
    val toId: Long? = null,
    val fromAmount: BigDecimal? = null,
    val toAmount: BigDecimal? = null,
    val personId: Long = 0
)

@RestController
@RequestMapping("/transactions", produces = ["application/json"])
class TransactionController(
        val transactionService: TransactionService,
        val radmintoolService: RadmintoolService
) {
    private val log = LoggerFactory.getLogger(javaClass)


    @PostMapping("/capture", consumes = ["application/json"])
    fun addNewCapture(@RequestBody captureRequestDTO: CaptureRequestDTO): ResponseEntity<Boolean> {
        log.debug("> addNewCapture - amount: {}, paidById: {}", captureRequestDTO.amount, captureRequestDTO.paidById)
        val isTransactionCaptured: Boolean = transactionService.captureTransaction(captureRequestDTO.amount, captureRequestDTO.paidById)

        radmintoolService.notifyThatNewTransactionWasCaptured()
        log.debug("< addNewCapture is ok")
        return ResponseEntity.ok(isTransactionCaptured)
    }

    @GetMapping("/longTransactions")
    fun getLongTransactionsHeaders() : String{
        return jacksonObjectMapper().writeValueAsString(FilterTransaction(showOnFilterDemandOnly = false))
    }

    @PostMapping("/longTransactions", consumes = ["application/json"])
    fun getLongTransactions(@RequestBody request: LongTransactionRequestDTO) : ResponseEntity<LongTransactionResponseDTO> {
        log.info("Request ${request.toString()}")
        val paginatedResult = transactionService.getTransactionsWithFilter(request)

        val response = LongTransactionResponseDTO(data = paginatedResult.content, paginatedResult.totalElements, paginatedResult.totalPages)
        log.info(response.toString())
        return ResponseEntity.ok(response)
    }
}
