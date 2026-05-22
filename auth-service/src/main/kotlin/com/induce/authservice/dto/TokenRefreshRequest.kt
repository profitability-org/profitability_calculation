package com.induce.authservice.dto

import jakarta.validation.constraints.NotBlank

data class TokenRefreshRequest(
    @field:NotBlank(message = "Refresh token cannot be blank")
    val refreshToken: String
)

