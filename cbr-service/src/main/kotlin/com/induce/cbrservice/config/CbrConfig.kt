package com.induce.cbrservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class CbrConfig {

    @Bean
    fun cbrInflationXmlUrl(): String {
        return "https://www.cbr.ru/secinfo/secinfo.asmx"
    }

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }
}
