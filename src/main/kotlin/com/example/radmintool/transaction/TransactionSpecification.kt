package com.example.radmintool.transaction

import com.example.radmintool.person.Person
import org.springframework.data.jpa.domain.Specification
import java.math.BigDecimal
import javax.persistence.criteria.Predicate

object TransactionSpecification {

    fun betweenId(idFrom: Long?, idTo: Long?): Specification<Transaction> {
        return Specification { root, query, cb ->
            val predicates = mutableListOf<Predicate>()

            idFrom?.let {
                val id = root.get<Long>("id")
                predicates.add(cb.greaterThan(id, idFrom))
            }
            idTo?.let {
                val id = root.get<Long>("id")
                predicates.add(cb.lessThan(id, idTo))
            }
            cb.and(*predicates.toTypedArray())
        }
    }

    fun betweenAmount(amountFrom: BigDecimal?, amountTo: BigDecimal?): Specification<Transaction> {
        return Specification { root, query, cb ->
            val predicates = mutableListOf<Predicate>()

            amountFrom?.let {
                val amount = root.get<BigDecimal>("amount")
                predicates.add(cb.greaterThan(amount, amountFrom))
            }
            amountTo?.let {
                val amount = root.get<BigDecimal>("amount")

                predicates.add(cb.lessThan(amount, amountTo))
            }
            cb.and(*predicates.toTypedArray())
        }
    }

    fun paidByPerson(paidBy: Person): Specification<Transaction> {
        return Specification { root, query, cb ->
            cb.equal(root.get<Person>("paidBy"), paidBy)
        }
    }
}
