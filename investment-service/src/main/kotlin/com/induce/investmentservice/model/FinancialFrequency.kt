package com.induce.investmentservice.model

enum class FinancialFrequency(val months: Int) {
    MONTHLY(1),
    QUARTERLY(3),
    SEMI_ANNUALLY(6),
    ANNUALLY(12)
}