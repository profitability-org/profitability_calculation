package com.induce.cbrservice.service

import com.induce.cbrservice.client.CbrApiClient
import com.induce.cbrservice.dto.CategoryDto
import com.induce.cbrservice.model.CbrJsonIndicator
import org.springframework.stereotype.Service

@Service
class CbrJsonService(
    private val client: CbrApiClient,
) {
    fun fetchCategories(): List<CategoryDto> {
        return client.getCategories().category
    }

    fun fetchDepositRateCategory(): CategoryDto? {
        return client.getCategories().category
            .firstOrNull { it.indicatorId == CbrJsonIndicator.DEPOSIT_RATE_PHYSICAL.id }
    }
}
