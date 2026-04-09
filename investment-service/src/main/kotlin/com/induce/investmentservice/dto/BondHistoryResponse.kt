package com.induce.investmentservice.dto

import com.induce.investmentservice.model.FinancialFrequency
import java.math.BigDecimal
import java.time.LocalDateTime

data class BondHistoryResponse(
    val id: Long,
    val title: String,
    val nominal: BigDecimal,
    val purchasePricePercent: BigDecimal,
    val couponRate: BigDecimal,
    val termMonths: Int,
    val taxRate: BigDecimal,
    val frequency: FinancialFrequency,
    val ytm: BigDecimal,
    val netYield: BigDecimal,
    val totalProfitAmount: BigDecimal,
    val totalProfitPercent: BigDecimal,
    val createdAt: LocalDateTime
)
