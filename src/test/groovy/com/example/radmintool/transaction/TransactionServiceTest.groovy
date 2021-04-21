package com.example.radmintool.transaction

import com.example.radmintool.person.Person
import com.example.radmintool.person.PersonRepository
import spock.lang.Specification

class TransactionServiceTest extends Specification {

    TransactionService transactionService

    TransactionRepository transactionRepository = Mock()
    PersonRepository personRepository = Mock()


    void setup() {
        transactionService = new TransactionService(transactionRepository, personRepository)
    }

    def "Capture any valid transaction"() {
        given:
        def person = new Person(1L, "firstname", "lastname", 25, 1L)

        personRepository.findById(_ as Long) >> Optional.of(person)
        transactionRepository.save(_)


        when:
        boolean result = transactionService.captureTransaction(BigDecimal.ONE, 1L)

        then:
        result == true
    }

    def "Capture transaction but finding no user"() {
        given:
        personRepository.findById(_ as Long) >> Optional.empty()

        when:
        boolean result = transactionService.captureTransaction(BigDecimal.ONE, 1L)

        then:
        result == false
    }
}
