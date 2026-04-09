package com.induce.investmentservice.repository

import com.induce.investmentservice.model.Stock
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface StockRepository : JpaRepository<Stock, Long> {
    fun findAllByUserId(userId: UUID): List<Stock>
}
