package com.example.radmintool.transaction

import com.example.radmintool.person.Person
import java.io.Serializable
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val createdDateTime: LocalDateTime = LocalDateTime.now(),
    val amount: BigDecimal,
    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val paidBy: Person
) : Serializable
