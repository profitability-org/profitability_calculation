package com.induce.investmentservice.service

import com.induce.investmentservice.model.InvestmentType
import java.util.UUID

interface CalculationStrategy<T, R> {
    fun getType(): InvestmentType
    fun calculateAndSave(request: T, userId: UUID): R
}
