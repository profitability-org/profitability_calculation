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
@Table(name = "stocks")
class Stock(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "user_id", nullable = false)
    val userId: UUID,

    val purchasePrice: BigDecimal,
    val targetPrice: BigDecimal,
    val holdingMonths: Int,
    val dividendRate: BigDecimal,
    val commission: BigDecimal,

    @Enumerated(EnumType.STRING)
    val frequency: FinancialFrequency,

    val totalYieldPercent: BigDecimal,
    val totalYieldAmount: BigDecimal,
    val dividendIncome: BigDecimal,
    val capitalGain: BigDecimal,
    val netYield: BigDecimal,

    val createdAt: LocalDateTime = LocalDateTime.now()
)