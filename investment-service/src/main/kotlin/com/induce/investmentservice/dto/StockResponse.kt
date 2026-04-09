package com.induce.investmentservice.dto

import com.induce.investmentservice.model.FinancialFrequency
import java.math.BigDecimal

data class StockResponse(
    val totalYieldPercent: BigDecimal,
    val totalYieldAmount: BigDecimal,
    val netYield: BigDecimal,
    val capitalGain: BigDecimal,
    val dividendIncome: BigDecimal,
    val frequency: FinancialFrequency,
    val portfolioGrowthGraph: List<BigDecimal>
)