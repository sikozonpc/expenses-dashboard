package com.sikozonpc.expensesdashboard.dto

import com.sikozonpc.expensesdashboard.annotation.IsTransactionCategory
import java.math.BigDecimal
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.NotBlank


data class TransactionDTO(
    val id: Int?,

    @get:NotBlank(message = "title must not be empty")
    val title: String = "",

    @get:DecimalMin("0", message = "amount must be a positive value")
    val amount: BigDecimal = BigDecimal(0),

    @get:IsTransactionCategory(message = "category must be one of 'WANT' or 'NEED'")
    val category: String? = "",
)