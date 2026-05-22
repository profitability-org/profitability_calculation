package com.induce.authservice.controller

import com.induce.authservice.dto.JwtResponse
import com.induce.authservice.dto.LoginRequest
import com.induce.authservice.dto.RegisterRequest
import com.induce.authservice.dto.TokenRefreshRequest
import com.induce.authservice.service.AuthService
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/register")
    fun register(@Valid @RequestBody request: RegisterRequest): ResponseEntity<Map<String, String>> {
        authService.register(request)
        return ResponseEntity.ok(mapOf("message" to "User registered successfully"))
    }

    @Operation(summary = "Вход в систему", description = "Возвращает Access и Refresh токены при успешной проверке учетных данных")
    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): ResponseEntity<JwtResponse> {
        val jwtResponse = authService.login(request)
        return ResponseEntity.ok(jwtResponse)
    }

    @Operation(summary = "Обновление токенов", description = "Принимает действующий Refresh токен, возвращает новую пару токенов")
    @PostMapping("/refresh")
    fun refreshToken(@Valid @RequestBody request: TokenRefreshRequest): ResponseEntity<JwtResponse> {
        val jwtResponse = authService.refresh(request)
        return ResponseEntity.ok(jwtResponse)
    }
}
