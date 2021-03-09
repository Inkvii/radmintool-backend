package com.example.radmintool.clientorganization

import com.example.radmintool.partyproperty.PartyProperty
import java.io.Serializable
import javax.persistence.*

@Entity
data class ClientOrganization(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val doingBusinessAs: String = "",
    val externalClientOrganizationId: String = "",
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val partyProperties: List<PartyProperty> = emptyList()
) : Serializable
