package com.induce.cbrservice.scheduler

import com.induce.cbrservice.service.CbrService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class MacroDataScheduler(private val cbrService: CbrService) {

    @Scheduled(cron = "0 0 1 * * *")
    fun dailyUpdate() {
        val now = LocalDate.now()
        val start = now.withDayOfMonth(1)
        val end = now.plusMonths(1).withDayOfMonth(1)

        println("Запуск планового обновления данных за $now")

        try {
            cbrService.refreshMacroIndicators(start, end)
        } catch (e: Exception) {
            println("Плановое обновление не удалось: ${e.message}")
        }
    }
}
