package com.example.radmintool.security

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class LoginDTO(val username: String, val password: String)

enum class Permission {
    REDUX_COUNTER,
    ADMIN_PAGE
}

data class AuthToken(
    val token: String,
    val issued: Long,
    val expires: Long,
    val permissions: List<Permission>
)


@RestController
@RequestMapping("/authentication", consumes = ["application/json"], produces = ["application/json"])
class AuthenticationController {

    @PostMapping("/login")
    fun login(@RequestBody login: LoginDTO): ResponseEntity<AuthToken?> {
        if (login.username.toLowerCase() == "admin" && login.password == "password") {
            return ResponseEntity.ok(
                AuthToken(
                    "Big bad admin",
                    System.currentTimeMillis(),
                    System.currentTimeMillis() + (5 * 60 * 1000),
                    listOf(Permission.REDUX_COUNTER, Permission.ADMIN_PAGE)
                )
            )
        }
        if (login.username == "user") {
            return ResponseEntity.ok(
                AuthToken(
                    "Big bad admin",
                    System.currentTimeMillis(),
                    System.currentTimeMillis() + (5 * 60 * 1000),
                    listOf(Permission.REDUX_COUNTER)
                )
            )
        } else return ResponseEntity.badRequest().build()
    }

    @PostMapping("/renewToken")
    fun renewToken(@RequestBody token: AuthToken?): ResponseEntity<AuthToken> {
        if (token != null) {
            return ResponseEntity.ok(
                token.copy(
                    issued = System.currentTimeMillis(),
                    expires = System.currentTimeMillis() + (5 * 60 * 1000),
                    permissions = token.permissions.toList()
                )
            )
        }
        return ResponseEntity.notFound().build()
    }
}
