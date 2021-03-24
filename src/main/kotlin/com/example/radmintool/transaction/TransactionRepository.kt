package com.example.radmintool.transaction

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.time.LocalDateTime

interface TransactionRepository : JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {

    fun findByCreatedDateTimeAfter(datetime: LocalDateTime): List<Transaction>


}
