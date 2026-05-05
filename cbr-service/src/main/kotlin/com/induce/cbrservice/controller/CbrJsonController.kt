package com.induce.cbrservice.controller

import com.induce.cbrservice.dto.CategoryDto
import com.induce.cbrservice.service.CbrJsonService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/cbr")
class CbrJsonController(
    private val service: CbrJsonService
) {

    @GetMapping("/categories")
    fun categories(): List<CategoryDto> {
        return service.fetchCategories()
    }

    @GetMapping("/deposit-rate")
    fun depositRate(): CategoryDto? {
        return service.fetchDepositRateCategory()
    }
}
