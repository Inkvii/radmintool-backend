package com.example.radmintool.transaction

import com.example.radmintool.person.Person
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.math.BigDecimal
import java.time.LocalDateTime

interface TransactionRepository : JpaRepository<Transaction, Long> {

    fun findByCreatedDateTimeAfter(datetime: LocalDateTime): List<Transaction>

    fun findTransactionsByIdBetweenAndAmountBetweenAndPaidBy(idMin: Long, idMax: Long, amountMin: BigDecimal, amountMax: BigDecimal, paidBy: Person, pageable: Pageable): Page<Transaction>
}
