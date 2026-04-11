package com.induce.investmentservice.service

import com.induce.investmentservice.dto.BondHistoryResponse
import com.induce.investmentservice.dto.BondRequest
import com.induce.investmentservice.dto.BondResponse
import com.induce.investmentservice.model.Bond
import com.induce.investmentservice.repository.BondRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.UUID

@Service
class BondService(
    private val bondRepository: BondRepository
) {

    fun calculate(request: BondRequest): BondResponse {
        val nominal = request.nominal
        val totalMonths = request.termMonths
        val paymentStep = request.frequency.months

        // 1. Цена покупки в деньгах
        val purchasePrice = nominal.multiply(request.purchasePricePercent)
            .divide(BigDecimal("100"), 2, RoundingMode.HALF_UP)

        // 2. Месячная ставка купона
        val monthlyCouponRate = request.couponRate
            .divide(BigDecimal("1200"), 10, RoundingMode.HALF_UP)

        val couponPaymentsGraph = mutableListOf<BigDecimal>()
        var accumulatedCoupons = BigDecimal.ZERO

        // 3. График купонов
        for (month in 1..totalMonths) {
            val interestForMonth = nominal.multiply(monthlyCouponRate)
            accumulatedCoupons = accumulatedCoupons.add(interestForMonth)

            if (month % paymentStep == 0 || month == totalMonths) {
                couponPaymentsGraph.add(accumulatedCoupons.setScale(2, RoundingMode.HALF_UP))
            }
        }

        // 4. Прибыль и налоги
        val priceGain = nominal.subtract(purchasePrice)
        val grossProfit = accumulatedCoupons.add(priceGain)

        val taxMultiplier = request.taxRate.divide(BigDecimal("100"), 10, RoundingMode.HALF_UP)
        val netProfit = if (grossProfit > BigDecimal.ZERO) {
            grossProfit.subtract(grossProfit.multiply(taxMultiplier))
        } else grossProfit

        // 5. Расчет доходностей (годовых)
        val termInYears = BigDecimal(totalMonths).divide(BigDecimal("12"), 10, RoundingMode.HALF_UP)

        val ytm = grossProfit.divide(purchasePrice, 10, RoundingMode.HALF_UP)
            .divide(termInYears, 10, RoundingMode.HALF_UP)
            .multiply(BigDecimal("100"))
            .setScale(2, RoundingMode.HALF_UP)

        val netYield = netProfit.divide(purchasePrice, 10, RoundingMode.HALF_UP)
            .divide(termInYears, 10, RoundingMode.HALF_UP)
            .multiply(BigDecimal("100"))
            .setScale(2, RoundingMode.HALF_UP)

        val totalProfitPercent = netProfit.divide(purchasePrice, 10, RoundingMode.HALF_UP)
            .multiply(BigDecimal("100"))
            .setScale(2, RoundingMode.HALF_UP)

        return BondResponse(
            title = request.title,
            ytm = ytm,
            netYield = netYield,
            totalProfitAmount = netProfit.setScale(2, RoundingMode.HALF_UP),
            totalProfitPercent = totalProfitPercent,
            frequency = request.frequency,
            couponPaymentsGraph = couponPaymentsGraph
        )
    }

    @Transactional
    fun calculateAndSave(request: BondRequest, userId: UUID): BondResponse {

        val response = calculate(request)
        // 6. Сохранение
        val bondEntity = Bond(
            userId = userId,
            title = request.title,
            nominal = request.nominal,
            purchasePricePercent = request.purchasePricePercent,
            couponRate = request.couponRate,
            termMonths = request.termMonths,
            taxRate = request.taxRate,
            frequency = request.frequency,
            ytm = response.ytm,
            netYield = response.netYield,
            totalProfitAmount = response.totalProfitAmount,
            totalProfitPercent = response.totalProfitPercent
        )
        bondRepository.save(bondEntity)

        return response
    }

    fun getAllByUser(userId: UUID): List<BondHistoryResponse> {
        return bondRepository.findAllByUserId(userId).map {
            BondHistoryResponse(
                id = it.id,
                title = it.title,
                nominal = it.nominal,
                purchasePricePercent = it.purchasePricePercent,
                couponRate = it.couponRate,
                termMonths = it.termMonths,
                taxRate = it.taxRate,
                frequency = it.frequency,
                ytm = it.ytm,
                netYield = it.netYield,
                totalProfitAmount = it.totalProfitAmount,
                totalProfitPercent = it.totalProfitPercent,
                createdAt = it.createdAt
            )
        }
    }
}
