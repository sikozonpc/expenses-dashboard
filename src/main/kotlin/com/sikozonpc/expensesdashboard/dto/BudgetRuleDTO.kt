package com.sikozonpc.expensesdashboard.dto

import javax.validation.constraints.DecimalMax
import javax.validation.constraints.DecimalMin


data class BudgetRuleDTO(
    val id: Int?,

    @get:DecimalMin("0", message = "needsPercentage must be a positive value")
    @get:DecimalMax("100", message = "needsPercentage must be between 0 and 100")
    val needsPercentage: String = "0",

    @get:DecimalMin("0", message = "wantsPercentage must be a positive value")
    @get:DecimalMax("100", message = "wantsPercentage must be between 0 and 100")
    val wantsPercentage: String = "0",
)