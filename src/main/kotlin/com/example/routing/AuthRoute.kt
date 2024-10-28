package com.example.routing

import com.example.routing.request.LoginRequest
import com.example.routing.request.RefreshTokenRequest
import com.example.routing.response.AuthResponse
import com.example.service.UserService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRoute(userService: UserService) {

    post {
        val loginRequest = call.receive<LoginRequest>()
        val authResponse: AuthResponse?= userService.autheticate(loginRequest)

        authResponse?.let {
            call.respond(authResponse)
        } ?: call.respond(HttpStatusCode.Unauthorized)
    }

    post("/refresh") {
        val request = call.receive<RefreshTokenRequest>()
        val newAccessToken: String? = userService.refreshToken(request.token)

        newAccessToken?.let {
            call.respond(
                RefreshTokenRequest(it)
            )
        }?: call.respond(message = HttpStatusCode.Unauthorized)
    }
}