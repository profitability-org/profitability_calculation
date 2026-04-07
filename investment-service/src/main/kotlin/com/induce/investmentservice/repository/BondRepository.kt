package com.induce.investmentservice.repository


import com.induce.investmentservice.model.Bond
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface BondRepository : JpaRepository<Bond, Long> {
    fun findAllByUserId(userId: UUID): List<Bond>
}
