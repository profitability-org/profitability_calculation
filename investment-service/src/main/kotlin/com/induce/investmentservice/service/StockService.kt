package com.induce.investmentservice.service

import com.induce.investmentservice.dto.StockHistoryResponse
import com.induce.investmentservice.dto.StockRequest
import com.induce.investmentservice.dto.StockResponse
import com.induce.investmentservice.model.Stock
import com.induce.investmentservice.repository.StockRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.UUID

@Service
class StockService(
    private val stockRepository: StockRepository
){
    fun calculate(request: StockRequest): StockResponse {
        val totalMonths = request.holdingMonths
        val step = request.frequency.months

        // 1. Расчет комиссий и затрат
        val commissionAmount = request.purchasePrice.multiply(request.commission)
            .divide(BigDecimal("100"), 10, RoundingMode.HALF_UP)
        val totalInvestment = request.purchasePrice.add(commissionAmount)

        // 2. Дивидендная логика (месячная ставка)
        val monthlyDivRate = request.dividendRate.divide(BigDecimal("1200"), 10, RoundingMode.HALF_UP)

        // 3. Логика роста цены (линейный прирост в месяц для графика)
        val totalCapitalGain = request.targetPrice.subtract(request.purchasePrice)
        val monthlyPriceGrowth = totalCapitalGain.divide(BigDecimal(totalMonths), 10, RoundingMode.HALF_UP)

        val graph = mutableListOf<BigDecimal>()
        var accumulatedDividends = BigDecimal.ZERO

        for (month in 1..totalMonths) {
            val divThisMonth = request.purchasePrice.multiply(monthlyDivRate)
            accumulatedDividends = accumulatedDividends.add(divThisMonth)

            if (month % step == 0 || month == totalMonths) {
                val currentPrice = request.purchasePrice.add(monthlyPriceGrowth.multiply(BigDecimal(month)))
                graph.add(currentPrice.add(accumulatedDividends).setScale(2, RoundingMode.HALF_UP))
            }
        }

        // 4. Налоги (на прибыль и дивы)
        val totalGrossProfit = totalCapitalGain.subtract(commissionAmount).add(accumulatedDividends)
        val taxMultiplier = request.taxRate.divide(BigDecimal("100"), 10, RoundingMode.HALF_UP)

        val netProfit = if (totalGrossProfit > BigDecimal.ZERO) {
            totalGrossProfit.multiply(BigDecimal.ONE.subtract(taxMultiplier))
        } else totalGrossProfit

        // 5. Доходности
        val termInYears = BigDecimal(totalMonths).divide(BigDecimal("12"), 10, RoundingMode.HALF_UP)
        val netYield = netProfit.divide(totalInvestment, 10, RoundingMode.HALF_UP)
            .divide(termInYears, 10, RoundingMode.HALF_UP)
            .multiply(BigDecimal("100"))
            .setScale(2, RoundingMode.HALF_UP)

        return StockResponse(
            title = request.title,
            totalYieldPercent = netProfit.divide(totalInvestment, 10, RoundingMode.HALF_UP).multiply(BigDecimal("100"))
                .setScale(2, RoundingMode.HALF_UP),
            totalYieldAmount = netProfit.setScale(2, RoundingMode.HALF_UP),
            netYield = netYield,
            capitalGain = totalCapitalGain.subtract(commissionAmount).setScale(2, RoundingMode.HALF_UP),
            dividendIncome = accumulatedDividends.setScale(2, RoundingMode.HALF_UP),
            frequency = request.frequency,
            portfolioGrowthGraph = graph
        )
    }
    @Transactional
    @CacheEvict(value = ["stockHistory"], key = "#userId")
    fun calculateAndSave(request: StockRequest, userId: UUID): StockResponse {
        val response = calculate(request)

        stockRepository.save(
            Stock(
                userId = userId,
                title = request.title,
                purchasePrice = request.purchasePrice,
                targetPrice = request.targetPrice,
                holdingMonths = request.holdingMonths,
                dividendRate = request.dividendRate,
                commission = request.commission,
                frequency = request.frequency,
                totalYieldPercent = response.totalYieldPercent,
                totalYieldAmount = response.totalYieldAmount,
                dividendIncome = response.dividendIncome,
                capitalGain = response.capitalGain,
                netYield = response.netYield
            )
        )

        return response
    }

    @Cacheable(value = ["stockHistory"], key = "#userId")
    fun getAllByUser(userId: UUID): List<StockHistoryResponse> {
        return stockRepository.findAllByUserId(userId).map {
            StockHistoryResponse(
                id = it.id,
                title = it.title,
                purchasePrice = it.purchasePrice,
                targetPrice = it.targetPrice,
                holdingMonths = it.holdingMonths,
                dividendRate = it.dividendRate,
                commission = it.commission,
                frequency = it.frequency,
                totalYieldPercent = it.totalYieldPercent,
                totalYieldAmount = it.totalYieldAmount,
                dividendIncome = it.dividendIncome,
                capitalGain = it.capitalGain,
                netYield = it.netYield,
                createdAt = it.createdAt
            )
        }
    }
}
