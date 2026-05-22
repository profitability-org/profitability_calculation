package com.induce.authservice.service

import com.induce.authservice.exception.TokenRefreshException
import com.induce.authservice.model.RefreshToken
import com.induce.authservice.model.User
import com.induce.authservice.repository.RefreshTokenRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.Date
import java.util.UUID

@Service
class JwtService(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.expiration}") private val accessTokenExpiration: Long,
    @Value("\${jwt.refresh-expiration}") private val refreshTokenExpiration: Long,
    private val refreshTokenRepository: RefreshTokenRepository
) {
    private val key by lazy { Keys.hmacShaKeyFor(secret.toByteArray()) }

    fun generateAccessToken(user: User): String {
        val now = Date()
        val expiryDate = Date(now.time + accessTokenExpiration)

        return Jwts.builder()
            .subject(user.id.toString())
            .claim("role", user.role.name)
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(key)
            .compact()
    }

    @Transactional
    fun createRefreshToken(user: User): RefreshToken {
        refreshTokenRepository.deleteByUser(user)

        val refreshToken = RefreshToken(
            token = UUID.randomUUID().toString(),
            user = user,
            expiryDate = Instant.now().plusMillis(refreshTokenExpiration)
        )

        return refreshTokenRepository.save(refreshToken)
    }

    fun verifyExpiration(token: RefreshToken): RefreshToken {
        if (token.isExpired()) {
            refreshTokenRepository.delete(token)
            throw TokenRefreshException(token.token, "Refresh token was expired. Please make a new signin request")
        }
        return token
    }
}
