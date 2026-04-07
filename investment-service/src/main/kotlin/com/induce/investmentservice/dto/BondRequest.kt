package com.induce.investmentservice.dto

import com.induce.investmentservice.model.FinancialFrequency
import jakarta.validation.constraints.*
import java.math.BigDecimal

data class BondRequest(
    @field:NotNull(message = "Nominal value is required")
    @field:DecimalMin(value = "0.0", inclusive = false)
    val nominal: BigDecimal,

    @field:NotNull(message = "Purchase price (%) is required")
    @field:DecimalMin(value = "0.0", inclusive = false)
    @field:DecimalMax(value = "200.0")
    val purchasePricePercent: BigDecimal,

    @field:NotNull(message = "Coupon rate is required")
    @field:DecimalMin(value = "0.0")
    val couponRate: BigDecimal,

    @field:NotNull(message = "Frequency is required")
    val frequency: FinancialFrequency,

    @field:NotNull(message = "Term in months is required")
    @field:Min(1)
    val termMonths: Int,

    @field:NotNull(message = "Tax rate is required")
    @field:DecimalMin(value = "0.0")
    @field:DecimalMax(value = "100.0")
    val taxRate: BigDecimal
)