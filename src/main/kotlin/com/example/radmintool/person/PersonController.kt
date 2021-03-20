package com.example.radmintool.person

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/person"], produces = ["application/json"])
class PersonController(
        private val personService: PersonService

) {
    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("/getAll")
    fun getPeople(): ResponseEntity<GetAllPeopleResponse> {
        val then = System.currentTimeMillis()
        log.warn("Started")
        val response = GetAllPeopleResponse(personService.getAllPeopleAsDTO())
        log.warn("Fetched")
        log.info("Call for /person/getAll took ${System.currentTimeMillis().minus(then)} ms")
        return ResponseEntity.ok(response)

    }

    @GetMapping("/{id}")
    fun getPersonById(@PathVariable("id") personId: Long): ResponseEntity<Person> {
        val then = System.currentTimeMillis()
        log.warn("Started")
        val response: Person = personService.getPerson(personId)
        log.warn("Fetched person: " + response)
        log.info("Call for /person/${personId} took ${System.currentTimeMillis().minus(then)} ms")
        return ResponseEntity.ok(response)
    }
}
