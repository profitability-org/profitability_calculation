package com.induce.investmentservice.dto

import com.induce.investmentservice.model.FinancialFrequency
import java.math.BigDecimal
import java.time.LocalDateTime

data class DepositHistoryResponse(
    val id: Long,
    val title: String,
    val amount: BigDecimal,
    val interestRate: BigDecimal,
    val termMonths: Int,
    val capitalization: Boolean,
    val frequency: FinancialFrequency,
    val finalAmount: BigDecimal,
    val accruedInterest: BigDecimal,
    val effectiveRate: BigDecimal,
    val createdAt: LocalDateTime
)
