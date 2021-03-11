package com.example.radmintool.utils

import com.example.radmintool.clientorganization.ClientOrganization
import com.example.radmintool.clientorganization.ClientOrganizationService
import com.example.radmintool.partyproperty.PartyProperty
import com.example.radmintool.partyproperty.PartyPropertyGroup
import com.example.radmintool.person.Person
import com.example.radmintool.person.PersonService
import org.springframework.stereotype.Service
import java.util.*

@Service
class DatabasePreparatorUtils(
    private val personService: PersonService,
    private val clientOrganizationService: ClientOrganizationService
) {
    fun addPeopleToDb() {
        personService.addPerson(Person(firstName = "Alois", lastName = "Jirásek", age = 127, clientOrganizationReference = 1))
        personService.addPerson(Person(firstName = "Barák", lastName = "Konislav", age = 15, clientOrganizationReference = 2))
        personService.addPerson(Person(firstName = "Pepa", lastName = "Franta", age = 41, clientOrganizationReference = 3))
        personService.addPerson(Person(firstName = "Radomír", lastName = "Dvořák", age = 68, clientOrganizationReference = 4))
        personService.addPerson(Person(firstName = "Vojtěch", lastName = "Sýk", age = 32, clientOrganizationReference = 5))
        personService.addPerson(Person(firstName = "TestA", lastName = "Sýk", age = 32, clientOrganizationReference = 6))
        personService.addPerson(Person(firstName = "TestB", lastName = "Testovič", age = 16, clientOrganizationReference = 7))
        personService.addPerson(Person(firstName = "TestC", lastName = "Testik", age = 17, clientOrganizationReference = 8))
        personService.addPerson(Person(firstName = "TestD", lastName = "Tester", age = 20, clientOrganizationReference = 9))
        personService.addPerson(Person(firstName = "TestE", lastName = "Test", age = 23, clientOrganizationReference = 10))
    }

    fun addClientOrgToDb() {
        clientOrganizationService.addClientOrganization(createClientOrg("aral", "99200002000"))
        clientOrganizationService.addClientOrganization(createClientOrg("real", "9970001000000"))
        clientOrganizationService.addClientOrganization(createClientOrg("EDEKA", "9876540654056"))
        clientOrganizationService.addClientOrganization(createClientOrg("EasyShopper", "975246554242"))
        clientOrganizationService.addClientOrganization(createClientOrg("REWE", "9705254065000"))
    }

    private fun createClientOrg(doingBusinessAs: String, extClientOrgId: String): ClientOrganization {
        val partyProperties = mutableListOf(
            PartyProperty(null, PartyPropertyGroup.BASIC_INFO, "Payment system", "PBPAY"),
            PartyProperty(null, PartyPropertyGroup.BASIC_INFO, "Url link", "https://google.cz"),
            PartyProperty(null, PartyPropertyGroup.BASIC_INFO, "Contact email", "someone@gmail.com"),
            PartyProperty(null, PartyPropertyGroup.SETTLEMENT, "Settlement type", "GROSS"),
            PartyProperty(null, PartyPropertyGroup.SETTLEMENT, "Settlement frequency", "DAILY"),
            PartyProperty(null, PartyPropertyGroup.SETTLEMENT, "Settlement target", "TARGET2"),
            PartyProperty(null, PartyPropertyGroup.PAYOUT, "Payout group", "IDK"),
            PartyProperty(null, PartyPropertyGroup.PAYOUT, "Bank account", "DE123456789897"),
        )

        for (i in 1..10) {
            partyProperties.add(
                PartyProperty(
                    null,
                    PartyPropertyGroup.EMAIL_TEMPLATES,
                    "Placeholder ${i}",
                    "/perth/config/email/${UUID.randomUUID()}"
                )
            )
        }

        val clientOrganization = ClientOrganization(
            null,
            doingBusinessAs,
            extClientOrgId,
            partyProperties
        )

        return clientOrganization
    }
}
