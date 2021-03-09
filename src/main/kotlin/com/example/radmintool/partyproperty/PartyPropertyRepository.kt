package com.example.radmintool.partyproperty

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PartyPropertyRepository : JpaRepository<PartyProperty, Long> {
}
