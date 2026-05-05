package com.induce.cbrservice.client

import com.induce.cbrservice.dto.CategoryNewResponse
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class CbrApiClient(
    private val restTemplate: RestTemplate
) {

    private val baseUrl = "http://www.cbr.ru/dataservice"

    fun getCategories(): CategoryNewResponse {
        return restTemplate.getForObject(
            "$baseUrl/categoryNew",
            CategoryNewResponse::class.java
        ) ?: throw RuntimeException("Empty response from CBR")
    }
}
