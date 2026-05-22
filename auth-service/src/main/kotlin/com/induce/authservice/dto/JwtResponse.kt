package com.induce.authservice.dto

data class JwtResponse(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String = "Bearer"
)
