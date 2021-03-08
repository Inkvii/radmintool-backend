package com.example.radmintool.person

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
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
        log.warn("Started")
        val response = GetAllPeopleResponse(personService.getAllPeopleAsDTO())
        log.warn("Fetched")

        return ResponseEntity(response, HttpStatus.OK)

    }
}
