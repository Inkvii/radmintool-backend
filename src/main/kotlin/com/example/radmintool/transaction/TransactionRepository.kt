package com.example.radmintool.transaction

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface TransactionRepository : JpaRepository<Transaction, Long> {

    fun findByCreatedDateTimeAfter(datetime: LocalDateTime): List<Transaction>
}
