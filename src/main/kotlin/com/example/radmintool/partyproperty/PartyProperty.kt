package com.example.radmintool.partyproperty

import javax.persistence.*

@Entity
data class PartyProperty(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Enumerated(EnumType.STRING)
    @Column(name = "PARTY_PROPERTY_GROUP")
    val group: PartyPropertyGroup,
    val name: String,
    val value: String

)

enum class PartyPropertyGroup {
    BASIC_INFO,
    EMAIL_TEMPLATES,
    SETTLEMENT,
    PAYOUT
}
