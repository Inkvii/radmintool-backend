package com.example.radmintool.transaction

import com.example.radmintool.radmintool.RadmintoolService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
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
        val fromId: Long = 0,
        val toId: Long = 0,
        val fromAmount: BigDecimal = BigDecimal(0),
        val toAmount: BigDecimal = BigDecimal(0),
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
        val paginatedResult = transactionService.getTransactionsWithFilter(request)

        val response = LongTransactionResponseDTO(data = paginatedResult.content, paginatedResult.totalElements, paginatedResult.totalPages)
        return ResponseEntity.ok(response)
    }
}
