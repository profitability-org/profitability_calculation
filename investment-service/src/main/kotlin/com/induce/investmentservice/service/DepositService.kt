package com.induce.investmentservice.service

import com.induce.investmentservice.dto.DepositRequest
import com.induce.investmentservice.dto.DepositResponse
import com.induce.investmentservice.model.Deposit
import com.induce.investmentservice.model.InvestmentType
import com.induce.investmentservice.repository.DepositRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.UUID

@Service
class DepositService(private val depositRepository: DepositRepository) :
    CalculationStrategy<DepositRequest, DepositResponse> {

    override fun getType() = InvestmentType.DEPOSIT

    @Transactional
    override fun calculateAndSave(request: DepositRequest, userId: UUID): DepositResponse {
        val compoundStep = request.frequency.months
        val monthlyRate = request.interestRate.divide(BigDecimal("1200"), 10, RoundingMode.HALF_UP)

        val graph = mutableListOf<BigDecimal>()
        var currentPrincipal = request.amount
        var accumulatedInterest = BigDecimal.ZERO

        for (month in 1..request.termMonths) {
            val interestForMonth = currentPrincipal.multiply(monthlyRate)
            accumulatedInterest = accumulatedInterest.add(interestForMonth)

            if (request.capitalization && month % compoundStep == 0) {
                currentPrincipal = currentPrincipal.add(accumulatedInterest)
                accumulatedInterest = BigDecimal.ZERO
            }

            if (month % compoundStep == 0 || month == request.termMonths) {
                graph.add(currentPrincipal.add(accumulatedInterest).setScale(2, RoundingMode.HALF_UP))
            }
        }

        val finalAmount = currentPrincipal.add(accumulatedInterest).setScale(2, RoundingMode.HALF_UP)
        val termInYears = BigDecimal(request.termMonths).divide(BigDecimal("12"), 10, RoundingMode.HALF_UP)
        val effectiveRate = finalAmount.divide(request.amount, 10, RoundingMode.HALF_UP)
            .subtract(BigDecimal.ONE).divide(termInYears, 10, RoundingMode.HALF_UP)
            .multiply(BigDecimal("100")).setScale(2, RoundingMode.HALF_UP)

        depositRepository.save(
            Deposit(
                userId = userId,
                title = request.title,
                amount = request.amount,
                interestRate = request.interestRate,
                termMonths = request.termMonths,
                capitalization = request.capitalization,
                frequency = request.frequency,
                finalAmount = finalAmount,
                accruedInterest = finalAmount.subtract(request.amount),
                effectiveRate = effectiveRate
            )
        )

        return DepositResponse(
            title = request.title,
            finalAmount = finalAmount,
            accruedInterest = finalAmount.subtract(request.amount),
            effectiveRate = effectiveRate,
            frequency = request.frequency,
            capitalGrowthGraph = graph,
        )
    }
}
