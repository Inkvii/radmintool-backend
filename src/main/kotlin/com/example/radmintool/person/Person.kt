package com.example.radmintool.person

import com.example.radmintool.transaction.Transaction
import java.io.Serializable
import javax.persistence.*


@Entity
data class Person(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val firstName: String = "",
    val lastName: String = "",
    val age: Int = 0,
    val clientOrganizationReference: Long? = null,

    @OneToMany(fetch = FetchType.LAZY)
    val transactionHistory: List<Transaction> = emptyList()
) : Serializable


