package com.example

import com.example.plugins.*
import com.example.repository.RefreshTokenRepository
import com.example.repository.UserRepository
import com.example.routing.configureRouting
import com.example.service.JwtService
import com.example.service.UserService
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val userRepository = UserRepository()
    val refreshTokenRepository = RefreshTokenRepository()
    val jwtService = JwtService(this, userRepository)
    val userService = UserService(userRepository, jwtService, refreshTokenRepository)

    configureSerialization()
    configureSecurity(jwtService)
    configureRouting(userService)
}