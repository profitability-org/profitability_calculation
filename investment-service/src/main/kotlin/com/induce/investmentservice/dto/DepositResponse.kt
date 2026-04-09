package com.induce.investmentservice.dto

import com.induce.investmentservice.model.FinancialFrequency
import java.math.BigDecimal

data class DepositResponse(
    val title: String,
    val finalAmount: BigDecimal,
    val accruedInterest: BigDecimal,
    val effectiveRate: BigDecimal,
    val frequency: FinancialFrequency,
    val capitalGrowthGraph: List<BigDecimal>
)
