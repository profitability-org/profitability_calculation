package com.induce.investmentservice.dto

import com.induce.investmentservice.model.FinancialFrequency
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class StockRequest(
    @field:NotNull val purchasePrice: BigDecimal,
    @field:NotNull val targetPrice: BigDecimal,
    @field:NotNull @field:Min(1) val holdingMonths: Int,
    @field:NotNull val dividendRate: BigDecimal,
    @field:NotNull val frequency: FinancialFrequency,
    @field:NotNull val commission: BigDecimal,
    @field:NotNull val taxRate: BigDecimal = BigDecimal("13.0")
)
