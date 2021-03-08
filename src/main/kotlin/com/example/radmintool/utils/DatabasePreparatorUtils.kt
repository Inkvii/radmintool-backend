package com.example.radmintool.utils

import com.example.radmintool.person.Person
import com.example.radmintool.person.PersonService
import org.springframework.stereotype.Service

@Service
class DatabasePreparatorUtils(
        private val personService: PersonService
) {
    fun addPeopleToDb() {
        personService.addPerson(Person(firstName = "Alois", lastName = "Jirásek", age = 127, clientOrganizationReference = 1))
        personService.addPerson(Person(firstName = "Barák", lastName = "Konislav", age = 15, clientOrganizationReference = 2))
        personService.addPerson(Person(firstName = "Pepa", lastName = "Franta", age = 41, clientOrganizationReference = 3))
        personService.addPerson(Person(firstName = "Radomír", lastName = "Dvořák", age = 68, clientOrganizationReference = 4))
        personService.addPerson(Person(firstName = "Vojtěch", lastName = "Sýk", age = 32, clientOrganizationReference = 5))
    }
}
