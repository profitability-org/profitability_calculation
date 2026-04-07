package com.induce.investmentservice.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "deposits")
class Deposit(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "user_id", nullable = false)
    val userId: UUID,

    val amount: BigDecimal,
    val interestRate: BigDecimal,
    val termMonths: Int,
    val capitalization: Boolean,

    @Enumerated(EnumType.STRING)
    val frequency: FinancialFrequency,

    val finalAmount: BigDecimal,
    val accruedInterest: BigDecimal,
    val effectiveRate: BigDecimal,

    val createdAt: LocalDateTime = LocalDateTime.now()
)