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
@Table(name = "bonds")
class Bond(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "user_id", nullable = false)
    val userId: UUID,

    @Column(precision = 19, scale = 2)
    val nominal: BigDecimal,

    @Column(precision = 5, scale = 2)
    val purchasePricePercent: BigDecimal,

    @Column(precision = 5, scale = 2)
    val couponRate: BigDecimal,

    val termMonths: Int,

    @Column(precision = 5, scale = 2)
    val taxRate: BigDecimal,

    @Enumerated(EnumType.STRING)
    val frequency: FinancialFrequency,

    @Column(precision = 5, scale = 2)
    val ytm: BigDecimal,

    @Column(precision = 5, scale = 2)
    val netYield: BigDecimal,

    @Column(precision = 19, scale = 2)
    val totalProfitAmount: BigDecimal,

    @Column(precision = 5, scale = 2)
    val totalProfitPercent: BigDecimal,

    val createdAt: LocalDateTime = LocalDateTime.now()
)
