package com.example.radmintool.person

import com.example.radmintool.transaction.Transaction
import com.fasterxml.jackson.annotation.JsonManagedReference
import java.io.Serializable
import javax.persistence.*


@Entity
@Table(name = "Person")
class Person(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    val id: Long? = null,
    @Column(name = "first_name")
    val firstName: String = "",
    @Column(name = "last_name")
    val lastName: String = "",
    @Column(name = "age")
    val age: Int = 0,
    @Column(name = "client_organization_reference_id")
    val clientOrganizationReference: Long? = null
) : Serializable {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "paidBy")
    @JsonManagedReference
    val transactionHistory: List<Transaction> = emptyList()
    override fun toString(): String {
        return "Person(id=$id, firstName='$firstName', lastName='$lastName', age=$age, clientOrganizationReference=$clientOrganizationReference)"
    }


}


