package com.example.radmintool.person

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class PersonService(
        private val repository: PersonRepository
) {
    private val log = LoggerFactory.getLogger(javaClass)

    fun getAllPeopleAsDTO(): List<PersonResponseDTO> {
        val allPeopleList = repository.findAll()

        val listOfDto = mutableListOf<PersonResponseDTO>()

        // Since model and dto is the same, this will give us little bit difference
        allPeopleList.forEach {
            listOfDto += PersonResponseDTO(
                id = (it.id ?: 0),
                firstName = it.firstName,
                lastName = it.lastName,
                age = it.age,
                clientOrganizationReferenceId = (it.clientOrganizationReference ?: 0)
            )
        }

        return listOfDto
    }

    fun addPerson(person: Person) {
        repository.save(person)
    }


    @Transactional
    fun getPerson(id: Long): Person {
        val person: Person = repository.getOne(id)
        person.transactionHistory.forEach({
            log.info("Transaction: ${it}")
        })
        return repository.getOne(id)
    }
}
