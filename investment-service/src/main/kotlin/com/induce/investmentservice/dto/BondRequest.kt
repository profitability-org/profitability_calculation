package com.induce.investmentservice.dto

import com.induce.investmentservice.model.FinancialFrequency
import jakarta.validation.constraints.DecimalMax
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.math.BigDecimal

data class BondRequest(
    @field:NotBlank(message = "Title is required")
    @field:Size(max = 20, message = "Title must be up to 20 characters")
    val title: String,

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
