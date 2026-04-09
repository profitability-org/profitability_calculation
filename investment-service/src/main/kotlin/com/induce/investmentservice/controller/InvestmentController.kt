package com.induce.investmentservice.controller

import com.induce.investmentservice.dto.BondHistoryResponse
import com.induce.investmentservice.dto.BondRequest
import com.induce.investmentservice.dto.BondResponse
import com.induce.investmentservice.dto.DepositHistoryResponse
import com.induce.investmentservice.dto.DepositRequest
import com.induce.investmentservice.dto.DepositResponse
import com.induce.investmentservice.dto.StockHistoryResponse
import com.induce.investmentservice.dto.StockRequest
import com.induce.investmentservice.dto.StockResponse
import com.induce.investmentservice.model.InvestmentType
import com.induce.investmentservice.service.BondService
import com.induce.investmentservice.service.CalculationFactory
import com.induce.investmentservice.service.DepositService
import com.induce.investmentservice.service.StockService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/investment")
class InvestmentController(
    private val calculationFactory: CalculationFactory,
    private val depositService: DepositService,
    private val stockService: StockService,
    private val bondService: BondService
) {

    @PostMapping("/deposit/add")
    fun calculateDeposit(
        @RequestHeader("X-Auth-User-Id") userId: UUID,
        @Valid @RequestBody request: DepositRequest
    ): ResponseEntity<DepositResponse> =
        ok(
            calculationFactory.getStrategy<DepositRequest, DepositResponse>(InvestmentType.DEPOSIT)
                .calculateAndSave(request, userId)
        )

    @PostMapping("/bond/add")
    fun calculateBond(
        @RequestHeader("X-Auth-User-Id") userId: UUID,
        @Valid @RequestBody request: BondRequest
    ): ResponseEntity<BondResponse> {
        val strategy = calculationFactory.getStrategy<BondRequest, BondResponse>(InvestmentType.BOND)
        return ok(strategy.calculateAndSave(request, userId))
    }

    @PostMapping("/stock/add")
    fun calculateStock(
        @RequestHeader("X-Auth-User-Id") userId: UUID,
        @Valid @RequestBody request: StockRequest
    ): ResponseEntity<StockResponse> {
        val strategy = calculationFactory.getStrategy<StockRequest, StockResponse>(InvestmentType.STOCK)
        return ok(strategy.calculateAndSave(request, userId))
    }

    @GetMapping("/deposit/list")
    fun getDeposits(@RequestHeader("X-Auth-User-Id") userId: UUID): ResponseEntity<List<DepositHistoryResponse>> =
        ok(depositService.getAllByUser(userId))

    @GetMapping("/bond/list")
    fun getBonds(@RequestHeader("X-Auth-User-Id") userId: UUID): ResponseEntity<List<BondHistoryResponse>> =
        ok(bondService.getAllByUser(userId))

    @GetMapping("/stock/list")
    fun getStocks(@RequestHeader("X-Auth-User-Id") userId: UUID): ResponseEntity<List<StockHistoryResponse>> =
        ok(stockService.getAllByUser(userId))
}
