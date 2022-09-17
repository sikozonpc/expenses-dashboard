package com.sikozonpc.expensesdashboard.dto

import com.sikozonpc.expensesdashboard.annotation.IsTransactionCategory
import java.math.BigDecimal
import javax.validation.constraints.DecimalMax
import javax.validation.constraints.DecimalMin


data class SpendingTargetDTO(
    val total: BigDecimal? = BigDecimal(0),

    val current: BigDecimal? = BigDecimal(0),

    val percentage: BigDecimal? = BigDecimal(0),

    val color: String? = "",

    @get:IsTransactionCategory(message = "category must be one of 'WANT' or 'NEED'")
    var category: String? = "",
)