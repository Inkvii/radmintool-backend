package com.example.radmintool.transaction

import com.example.radmintool.person.Person
import com.example.radmintool.person.PersonRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Service
class TransactionService(
        val transactionRepository: TransactionRepository,
        val personRepository: PersonRepository
) {

    fun captureTransaction(amount: BigDecimal, paidBy: Long): Boolean {
        val person: Optional<Person> = personRepository.findById(paidBy)
        if (person.isEmpty) {
            return false
        }
        val transaction = Transaction(amount = amount, paidBy = person.get())
        transactionRepository.save(transaction)

        return true
    }

    fun getTransactionsInLast5Minutes(): List<Transaction> {
        return transactionRepository.findByCreatedDateTimeAfter(LocalDateTime.now().minusMinutes(5))
    }

    fun getTransactionsWithFilter(request: LongTransactionRequestDTO): Page<Transaction> {
        val person = personRepository.findById(request.filter.personId)
        if (person.isEmpty) {
            return Page.empty()
        }

        val result = transactionRepository.findTransactionsByIdBetweenAndAmountBetweenAndPaidBy(
                request.filter.fromId, request.filter.toId, request.filter.fromAmount, request.filter.toAmount, person.get(),
                PageRequest.of(request.pageIndex, 100)
        )

        return result
    }
}
