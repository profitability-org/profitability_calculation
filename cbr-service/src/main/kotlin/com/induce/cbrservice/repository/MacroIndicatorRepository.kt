package com.induce.cbrservice.repository

import com.induce.cbrservice.model.MacroIndicator
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface MacroIndicatorRepository : JpaRepository<MacroIndicator, Long> {
    fun existsByRecordDate(recordDate: LocalDate): Boolean

    fun findFirstByOrderByRecordDateDesc(): MacroIndicator?

    fun findAllByRecordDateBetweenOrderByRecordDateAsc(start: LocalDate, end: LocalDate): List<MacroIndicator>
}
