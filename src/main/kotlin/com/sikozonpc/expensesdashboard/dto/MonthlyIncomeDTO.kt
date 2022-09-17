package com.sikozonpc.expensesdashboard.dto

import java.math.BigDecimal
import javax.validation.constraints.DecimalMin

data class MonthlyIncomeDTO(
    val id: Int?,

    val title: String? = "",

    @get:DecimalMin("0", message = "amount must be a positive value")
    val amount: BigDecimal = BigDecimal(0),

    val isConstant: Boolean? = true,
)