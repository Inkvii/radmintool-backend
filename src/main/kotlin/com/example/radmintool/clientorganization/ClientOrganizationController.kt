package com.example.radmintool.clientorganization

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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

    @PostMapping("/{id}/update", consumes = ["application/json"])
    fun updateClientOrganizationById(
        @PathVariable("id") clientOrganizationId: Long,
        @RequestBody changedColumns: ArrayList<UpdateClientOrg>
    ): ResponseEntity<String> {
        log.info("Yaay something is incoming: ${clientOrganizationId}, size: ${changedColumns.size}")
        changedColumns.forEach {
            log.info("Key: ${it.name}, value: ${it.value}")
        }
        return ResponseEntity.ok("It has been done")
    }

    data class UpdateClientOrg(
        val name: String,
        val value: String
    )
}
