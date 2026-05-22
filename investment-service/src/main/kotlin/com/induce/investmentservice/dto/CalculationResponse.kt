package com.induce.investmentservice.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class CalculationResponse(
    val amount: BigDecimal,
    val nominalRate: BigDecimal,
    val realProfit: BigDecimal,
    val calculatedAt: LocalDateTime = LocalDateTime.now()
)
