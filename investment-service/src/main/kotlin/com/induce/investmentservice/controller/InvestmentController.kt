package com.induce.investmentservice.controller

import com.induce.investmentservice.dto.CalculationRequest
import com.induce.investmentservice.dto.CalculationResponse
import com.induce.investmentservice.service.InvestmentCalculationService
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/investment/macro")
class InvestmentController(
    private val calculationService: InvestmentCalculationService
) {
    private val logger = LoggerFactory.getLogger(InvestmentController::class.java)

    @Operation(
        summary = "Расчет реального дохода с учетом инфляции",
        description = "Принимает сумму и номинальную ставку, запрашивает инфляцию по gRPC и возвращает чистый доход"
    )
    @PostMapping("/calculate")
    fun calculate(
        @Valid @RequestBody request: CalculationRequest
    ): ResponseEntity<CalculationResponse> {
        logger.info("Получен внешний запрос на расчет макро-доходности для суммы ${request.amount}")

        val realProfit = calculationService.calculateRealProfit(
            amount = request.amount,
            nominalRate = request.nominalRate
        )

        return ResponseEntity.ok(
            CalculationResponse(
                amount = request.amount,
                nominalRate = request.nominalRate,
                realProfit = realProfit
            )
        )
    }
}
