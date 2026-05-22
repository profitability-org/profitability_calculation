package com.induce.authservice.service

import com.induce.authservice.dto.JwtResponse
import com.induce.authservice.dto.LoginRequest
import com.induce.authservice.dto.RegisterRequest
import com.induce.authservice.dto.TokenRefreshRequest
import com.induce.authservice.exception.InvalidCredentialsException
import com.induce.authservice.exception.TokenRefreshException
import com.induce.authservice.exception.UserAlreadyExistsException
import com.induce.authservice.exception.UserNotFoundException
import com.induce.authservice.model.Role
import com.induce.authservice.model.User
import com.induce.authservice.repository.RefreshTokenRepository
import com.induce.authservice.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val jwtService: JwtService,
) {

    @Transactional
    fun register(request: RegisterRequest): User {
        if (userRepository.findByEmail(request.email) != null) {
            throw UserAlreadyExistsException(request.email)
        }

        val hash = passwordEncoder.encode(request.password)!!

        val newUser = User(
            email = request.email,
            passwordHash = hash,
            role = request.role ?: Role.USER,
        )

        return userRepository.save(newUser)
    }

    @Transactional
    fun login(request: LoginRequest): JwtResponse {
        val user = userRepository.findByEmail(request.email)
            ?: throw UserNotFoundException(request.email)

        if (!passwordEncoder.matches(request.password, user.passwordHash)) {
            throw InvalidCredentialsException()
        }

        val accessToken = jwtService.generateAccessToken(user)
        val refreshToken = jwtService.createRefreshToken(user)

        return JwtResponse(
            accessToken = accessToken,
            refreshToken = refreshToken.token
        )
    }

    @Transactional
    fun refresh(request: TokenRefreshRequest): JwtResponse {
        val requestToken = request.refreshToken

        val token = refreshTokenRepository.findByToken(requestToken)
            ?: throw TokenRefreshException(requestToken, "Refresh token is not in database!")

        val verifiedToken = jwtService.verifyExpiration(token)
        val user = verifiedToken.user

        val newAccessToken = jwtService.generateAccessToken(user)
        val newRefreshToken = jwtService.createRefreshToken(user)

        return JwtResponse(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken.token
        )
    }
}
