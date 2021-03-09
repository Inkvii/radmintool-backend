package com.example.radmintool.clientorganization

import org.springframework.stereotype.Service

@Service
class ClientOrganizationService(
    private val repository: ClientOrganizationRepository
) {
    fun getClientOrganization(id: Long): ClientOrganization {
        val clientOrganization: ClientOrganization = repository.getOne(id)
        return clientOrganization
    }

    fun addClientOrganization(clientOrganization: ClientOrganization) {
        repository.save(clientOrganization)
    }

}
