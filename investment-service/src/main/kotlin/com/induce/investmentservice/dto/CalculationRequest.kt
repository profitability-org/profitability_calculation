package com.induce.investmentservice.dto

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class CalculationRequest(
    @field:NotNull(message = "Сумма инвестиций не может быть пустой")
    @field:DecimalMin(value = "0.01", message = "Сумма инвестиций должна быть больше нуля")
    val amount: BigDecimal,

    @field:NotNull(message = "Номинальная ставка не может быть пустой")
    @field:DecimalMin(value = "0.0", message = "Номинальная ставка не может быть отрицательной")
    val nominalRate: BigDecimal
)
