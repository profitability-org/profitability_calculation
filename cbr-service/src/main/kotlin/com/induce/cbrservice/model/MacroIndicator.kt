package com.induce.cbrservice.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "macro_indicators")
class MacroIndicator(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    val recordDate: LocalDate,

    @Column(nullable = false)
    val keyRate: Double,

    @Column(nullable = false)
    val inflationValue: Double,

    @Column(nullable = false)
    val targetValue: Double,

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)
