package com.induce.cbrservice.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper

data class MacroIndicatorData(
    @JacksonXmlElementWrapper(useWrapping = false)
    @JsonProperty("RI")
    val records: List<MacroIndicatorRecord>
)

data class MacroIndicatorRecord(
    @JsonProperty("DTS")
    val date: String,

    @JsonProperty("KeyRate")
    val keyRate: Double,

    @JsonProperty("infVal")
    val inflationValue: Double,

    @JsonProperty("AimVal")
    val targetValue: Double
)
