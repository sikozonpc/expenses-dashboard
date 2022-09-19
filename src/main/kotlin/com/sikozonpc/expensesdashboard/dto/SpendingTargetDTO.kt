package com.sikozonpc.expensesdashboard.dto

import java.math.BigDecimal


data class SpendingTargetDTO(
    val total: BigDecimal? = BigDecimal(0),

    val current: BigDecimal? = BigDecimal(0),

    val percentage: BigDecimal? = BigDecimal(0),

    val color: String? = "",

    val category: String = "",
)