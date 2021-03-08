package com.example.radmintool.person

import org.springframework.stereotype.Service

@Service
class PersonService(
        private val repository: PersonRepository
) {

    fun getAllPeopleAsDTO(): List<PersonResponseDTO> {
        val allPeopleList = repository.findAll()

        val listOfDto = mutableListOf<PersonResponseDTO>()

        // Since model and dto is the same, this will give us little bit difference
        allPeopleList.forEach {
            listOfDto += PersonResponseDTO(
                    id = (it.id?.plus(1000000) ?: 0),
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
}
