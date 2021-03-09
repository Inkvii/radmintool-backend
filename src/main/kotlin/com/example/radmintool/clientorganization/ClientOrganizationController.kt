package com.example.radmintool.clientorganization

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/clientOrganization"], produces = ["application/json"])
class ClientOrganizationController(
    val clientOrganizationService: ClientOrganizationService
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("/{id}")
    fun getClientOrganizationById(@PathVariable("id") clientOrganizationId: Long): ResponseEntity<ClientOrganization> {
        val then = System.currentTimeMillis()
        log.warn("Started get client organization by id")
        val clientOrganization = clientOrganizationService.getClientOrganization(clientOrganizationId)
        log.warn("Fetched")
        log.info("Call for /clientOrganization/${clientOrganizationId} took ${System.currentTimeMillis().minus(then)} ms")
        return ResponseEntity.ok(clientOrganization)
    }
}
