package com.induce.investmentservice.service

import com.induce.investmentservice.grpc.CbrGrpcClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode

@Service
class InvestmentCalculationService(
    private val cbrGrpcClient: CbrGrpcClient
) {
    private val logger = LoggerFactory.getLogger(InvestmentCalculationService::class.java)

    fun calculateRealProfit(amount: BigDecimal, nominalRate: BigDecimal): BigDecimal {
        logger.info("Calculating real profit for amount: $amount with nominal rate: $nominalRate%")

        val inflationRate = cbrGrpcClient.fetchCurrentInflationRate()

        val realRate = nominalRate.subtract(inflationRate)
        logger.info("Calculated real interest rate: $realRate% (Nominal: $nominalRate% - Inflation: $inflationRate%)")

        if (realRate <= BigDecimal.ZERO) {
            logger.warn("Inflation eats all profit! Real rate is negative or zero: $realRate")
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)
        }

        return amount.multiply(realRate)
            .divide(BigDecimal("100"), 2, RoundingMode.HALF_UP)
            .also { logger.info("Calculation complete. Real profit amount: $it") }
    }
}

