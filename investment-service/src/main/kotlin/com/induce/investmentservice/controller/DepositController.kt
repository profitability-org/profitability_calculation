package com.induce.investmentservice.controller

import com.induce.investmentservice.dto.DepositHistoryResponse
import com.induce.investmentservice.dto.DepositRequest
import com.induce.investmentservice.dto.DepositResponse
import com.induce.investmentservice.service.DepositService
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
@RequestMapping("api/investment/deposit")
class DepositController(
    private val depositService: DepositService,
) {
    @PostMapping("/save")
    fun saveDeposit(
        @RequestHeader("X-Auth-User-Id") userId: UUID,
        @Valid @RequestBody request: DepositRequest
    ): ResponseEntity<DepositResponse> =
        ok(depositService.calculateAndSave(request, userId))

    @PostMapping("/calculate")
    fun calculateDeposit(
        @Valid @RequestBody request: DepositRequest
    ): ResponseEntity<DepositResponse> =
        ok(depositService.calculate(request))

    @GetMapping("/list")
    fun getDeposits(@RequestHeader("X-Auth-User-Id") userId: UUID): ResponseEntity<List<DepositHistoryResponse>> =
        ok(depositService.getAllByUser(userId))

}
