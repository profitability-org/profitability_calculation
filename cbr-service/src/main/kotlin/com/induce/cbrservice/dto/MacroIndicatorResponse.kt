package com.induce.cbrservice.dto

import com.induce.cbrservice.model.MacroIndicator
import java.time.LocalDate

data class MacroIndicatorResponse(
    val date: LocalDate,
    val keyRate: Double,
    val inflation: Double,
    val target: Double
)

fun MacroIndicator.toResponse() = MacroIndicatorResponse(
    date = this.recordDate,
    keyRate = this.keyRate,
    inflation = this.inflationValue,
    target = this.targetValue
)
