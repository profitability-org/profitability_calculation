package com.induce.cbrservice.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class CategoryNewResponse(
    val category: List<CategoryDto>
)

data class CategoryDto(
    @JsonProperty("category_id")
    val categoryId: Int?,

    @JsonProperty("category_name")
    val categoryName: String?,

    @JsonProperty("indicator_id")
    val indicatorId: Int?,

    @JsonProperty("indicator_name")
    val indicatorName: String?,

    val link: String? = null,

    @JsonProperty("begin_dt")
    val beginDt: Int? = null,

    @JsonProperty("end_dt")
    val endDt: Int? = null,

    @JsonProperty("indicator_parent")
    val indicatorParent: Int? = null
)
