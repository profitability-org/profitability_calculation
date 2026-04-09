package com.induce.investmentservice.dto

import com.induce.investmentservice.model.FinancialFrequency
import java.math.BigDecimal

data class BondResponse(
    val title: String,
    val ytm: BigDecimal,
    val netYield: BigDecimal,
    val totalProfitAmount: BigDecimal,
    val totalProfitPercent: BigDecimal,
    val frequency: FinancialFrequency,
    val couponPaymentsGraph: List<BigDecimal>
)
