package com.example.routing.request

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequest(
    val token : String
)
