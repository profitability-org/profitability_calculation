package com.induce.investmentservice.controller

import com.induce.investmentservice.dto.StockHistoryResponse
import com.induce.investmentservice.dto.StockRequest
import com.induce.investmentservice.dto.StockResponse
import com.induce.investmentservice.service.StockService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("api/investment/stock")
class StockController(
    private val stockService: StockService,
) {
    @PostMapping("/save")
    fun saveStock(
        @RequestHeader("X-Auth-User-Id") userId: UUID,
        @Valid @RequestBody request: StockRequest
    ): ResponseEntity<StockResponse> =
        ok(stockService.calculateAndSave(request, userId))

    @PostMapping("/calculate")
    fun calculateStock(
        @Valid @RequestBody request: StockRequest
    ): ResponseEntity<StockResponse> =
        ok(stockService.calculate(request))

    @GetMapping("/list")
    fun getStocks(@RequestHeader("X-Auth-User-Id") userId: UUID): ResponseEntity<List<StockHistoryResponse>> =
        ok(stockService.getAllByUser(userId))
}
