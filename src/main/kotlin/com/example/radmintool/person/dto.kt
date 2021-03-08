package com.example.radmintool.person

data class PersonResponseDTO(
        val id: Long,
        val firstName: String,
        val lastName: String,
        val age: Int,
        val clientOrganizationReferenceId: Long

)

data class PersonResponseHeader(
        val id: String,
        val displayName: String
)

data class GetAllPeopleResponse(
        val rows: List<PersonResponseDTO>
) {
    val headers = arrayOf(
            PersonResponseHeader("id", "id"),
            PersonResponseHeader("firstName", "First name"),
            PersonResponseHeader("lastName", "Last name"),
            PersonResponseHeader("age", "Age"),
            PersonResponseHeader("clientOrganizationReferenceId", "Client Org. Reference id"),
    )
}
