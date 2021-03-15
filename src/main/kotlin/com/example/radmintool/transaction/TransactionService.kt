package com.example.radmintool.transaction

import com.example.radmintool.person.Person
import com.example.radmintool.person.PersonRepository
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
}
