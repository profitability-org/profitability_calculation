package com.induce.investmentservice.service

import com.induce.investmentservice.model.InvestmentType
import org.springframework.stereotype.Component

@Component
class CalculationFactory(strategyList: List<CalculationStrategy<*, *>>) {

    private val strategies: Map<InvestmentType, CalculationStrategy<*, *>> =
        strategyList.associateBy { it.getType() }

    @Suppress("UNCHECKED_CAST")
    fun <T, R> getStrategy(type: InvestmentType): CalculationStrategy<T, R> {
        return (strategies[type] ?: throw IllegalArgumentException("No strategy for: $type"))
                as CalculationStrategy<T, R>
    }
}
