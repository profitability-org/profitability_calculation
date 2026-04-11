package com.induce.investmentservice.controller

import com.induce.investmentservice.dto.BondHistoryResponse
import com.induce.investmentservice.dto.BondRequest
import com.induce.investmentservice.dto.BondResponse
import com.induce.investmentservice.service.BondService
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
@RequestMapping("api/investment/bond")
class BondController(
    private val bondService: BondService,
) {
    @PostMapping("/save")
    fun saveBond(
        @RequestHeader("X-Auth-User-Id") userId: UUID,
        @Valid @RequestBody request: BondRequest
    ): ResponseEntity<BondResponse> =
        ok(bondService.calculateAndSave(request, userId))

    @PostMapping("/calculate")
    fun calculateBond(
        @Valid @RequestBody request: BondRequest
    ): ResponseEntity<BondResponse> =
        ok(bondService.calculate(request))

    @GetMapping("/list")
    fun getBonds(@RequestHeader("X-Auth-User-Id") userId: UUID): ResponseEntity<List<BondHistoryResponse>> =
        ok(bondService.getAllByUser(userId))

}
