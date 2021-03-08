package com.example.radmintool.person

import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@Entity
data class Person(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        val firstName: String = "",
        val lastName: String = "",
        val age: Int = 0,
        val clientOrganizationReference: Long? = null
) : Serializable


