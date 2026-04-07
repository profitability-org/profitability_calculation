package com.induce.investmentservice.repository

import com.induce.investmentservice.model.Deposit
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface DepositRepository : JpaRepository<Deposit, Long> {
    fun findAllByUserId(userId: UUID): List<Deposit>
}
