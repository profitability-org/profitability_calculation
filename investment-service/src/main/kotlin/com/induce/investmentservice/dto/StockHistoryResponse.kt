package com.induce.investmentservice.dto

import com.induce.investmentservice.model.FinancialFrequency
import java.math.BigDecimal
import java.time.LocalDateTime

data class StockHistoryResponse(
    val id: Long,
    val title: String,
    val purchasePrice: BigDecimal,
    val targetPrice: BigDecimal,
    val holdingMonths: Int,
    val dividendRate: BigDecimal,
    val commission: BigDecimal,
    val frequency: FinancialFrequency,
    val totalYieldPercent: BigDecimal,
    val totalYieldAmount: BigDecimal,
    val dividendIncome: BigDecimal,
    val capitalGain: BigDecimal,
    val netYield: BigDecimal,
    val createdAt: LocalDateTime
)
