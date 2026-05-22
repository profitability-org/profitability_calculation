package com.induce.authservice.repository

import com.induce.authservice.model.RefreshToken
import com.induce.authservice.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface RefreshTokenRepository : JpaRepository<RefreshToken, UUID> {

    fun findByToken(token: String): RefreshToken?

    @Modifying
    fun deleteByUser(user: User): Int
}

