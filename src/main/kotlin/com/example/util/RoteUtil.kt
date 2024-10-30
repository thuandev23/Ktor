package com.example.util

import com.example.plugins.RoleBasedAuthorizationPlugin
import io.ktor.server.routing.*

fun Route.autherized(
    vararg hasAnyRole: String,
    build: Route.() -> Unit
){
    install(RoleBasedAuthorizationPlugin){ roles = hasAnyRole.toSet() }
    build()
}