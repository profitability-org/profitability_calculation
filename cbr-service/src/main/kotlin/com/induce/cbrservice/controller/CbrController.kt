package com.induce.cbrservice.controller

import com.induce.cbrservice.dto.MacroIndicatorResponse
import com.induce.cbrservice.service.CbrService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/api/cbr")
class CbrController(private val cbrService: CbrService) {

    @GetMapping("/latest")
    fun getLatest(): MacroIndicatorResponse {
        return cbrService.getLatestIndicators()
    }

    @GetMapping("/history")
    fun getHistory(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) start: LocalDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) end: LocalDate
    ): List<MacroIndicatorResponse> {
        return cbrService.getDataForPeriod(start, end)
    }
}
