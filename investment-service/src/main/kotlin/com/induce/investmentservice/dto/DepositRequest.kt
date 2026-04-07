package com.induce.investmentservice.dto

import com.induce.investmentservice.model.FinancialFrequency
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class DepositRequest(
    @field:NotNull(message = "Deposit amount is required")
    @field:DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
    val amount: BigDecimal,

    @field:NotNull(message = "Interest rate is required")
    @field:DecimalMin(value = "0.0", message = "Interest rate cannot be negative")
    val interestRate: BigDecimal,

    @field:NotNull(message = "Term in months is required")
    @field:Min(value = 1, message = "Term must be at least 1 month")
    val termMonths: Int,

    val capitalization: Boolean = false,

    @field:NotNull(message = "Compounding frequency is required")
    val frequency: FinancialFrequency
)