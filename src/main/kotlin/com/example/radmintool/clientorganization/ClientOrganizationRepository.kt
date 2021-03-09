package com.example.radmintool.clientorganization

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientOrganizationRepository : JpaRepository<ClientOrganization, Long> {
}
