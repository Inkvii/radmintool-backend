package com.example.radmintool.transaction

import com.example.radmintool.person.Person
import com.fasterxml.jackson.annotation.JsonBackReference
import org.hibernate.annotations.CreationTimestamp
import java.io.Serializable
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "Transaction")
class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    val id: Long? = null,
    @CreationTimestamp
    @Column(name = "created_datetime")
    val createdDateTime: LocalDateTime = LocalDateTime.now(),
    @Column(name = "amount")
    val amount: BigDecimal,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    val paidBy: Person
) : Serializable {
    override fun toString(): String {
        return "Transaction(id=$id, createdDateTime=$createdDateTime, amount=$amount, paidBy=$paidBy)"
    }
}
