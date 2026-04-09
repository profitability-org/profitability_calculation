package com.induce.investmentservice.dto

import com.induce.investmentservice.model.FinancialFrequency
import jakarta.validation.constraints.DecimalMax
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.math.BigDecimal

data class StockRequest(
    @field:NotBlank(message = "Title is required")
    @field:Size(max = 20, message = "Title must be up to 20 characters")
    val title: String,

    @field:NotNull(message = "Purchase price is required")
    @field:DecimalMin(value = "0.0", inclusive = false, message = "Purchase price must be greater than zero")
    val purchasePrice: BigDecimal,

    @field:NotNull(message = "Target price is required")
    @field:DecimalMin(value = "0.0", inclusive = false, message = "Target price must be greater than zero")
    val targetPrice: BigDecimal,

    @field:NotNull(message = "Holding period is required")
    @field:Min(value = 1, message = "Holding period must be at least 1 month")
    val holdingMonths: Int,

    @field:NotNull(message = "Dividend rate is required")
    @field:DecimalMin(value = "0.0", message = "Dividend rate cannot be negative")
    val dividendRate: BigDecimal,

    @field:NotNull(message = "Dividend frequency is required")
    val frequency: FinancialFrequency,

    @field:NotNull(message = "Commission is required")
    @field:DecimalMin(value = "0.0", message = "Commission cannot be negative")
    val commission: BigDecimal,

    @field:NotNull(message = "Tax rate is required")
    @field:DecimalMin(value = "0.0", message = "Tax rate cannot be negative")
    @field:DecimalMax(value = "100.0", message = "Tax rate cannot exceed 100%")
    val taxRate: BigDecimal = BigDecimal("13.0")
)
