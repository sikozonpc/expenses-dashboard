package com.sikozonpc.expensesdashboard.dto

import javax.validation.constraints.DecimalMax
import javax.validation.constraints.DecimalMin


data class BudgetRuleDTO(
    val id: Int?,

    @get:DecimalMin("0", message = "essentialsGoal must be a positive value")
    @get:DecimalMax("100", message = "essentialsGoal must be between 0 and 100")
    var essentialsGoal: String,

    @get:DecimalMin("0", message = "haveToHaveGoal must be a positive value")
    @get:DecimalMax("100", message = "haveToHaveGoal must be between 0 and 100")
    var haveToHaveGoal: String,

    @get:DecimalMin("0", message = "niceToHaveGoal must be a positive value")
    @get:DecimalMax("100", message = "niceToHaveGoal must be between 0 and 100")
    var niceToHaveGoal: String,

    @get:DecimalMin("0", message = "savingsGoal must be a positive value")
    @get:DecimalMax("100", message = "savingsGoal must be between 0 and 100")
    var savingsGoal: String,
)
