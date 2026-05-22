package com.induce.cbrservice

import com.induce.cbrservice.service.CbrService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling
import java.time.LocalDate

@SpringBootApplication
@EnableScheduling
class CbrServiceApplication {

    @Bean
    fun initMacroData(cbrService: CbrService): CommandLineRunner = CommandLineRunner {
        val now = LocalDate.now()
        val sixMonthsAgo = now.minusMonths(6)

        println("=== Старт приложения: запуск первичной загрузки макроданных ЦБ с $sixMonthsAgo по $now ===")

        runCatching {
            cbrService.refreshMacroIndicators(sixMonthsAgo, now)
        }.onSuccess {
            println("=== Первичная загрузка данных за полгода успешно завершена ===")
        }.onFailure { e ->
            println("Предупреждение: Не удалось загрузить стартовые данные за полгода: ${e.message}")
        }
    }
}

fun main(args: Array<String>) {
    runApplication<CbrServiceApplication>(*args)
}
