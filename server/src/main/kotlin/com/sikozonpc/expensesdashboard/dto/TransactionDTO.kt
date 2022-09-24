package com.sikozonpc.expensesdashboard.dto

import com.sikozonpc.expensesdashboard.annotation.IsTransactionImportanceType
import com.sikozonpc.expensesdashboard.entity.TransactionsImportance
import java.math.BigDecimal
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.NotBlank

data class TransactionDTO(
    val id: Int?,

    @get:NotBlank(message = "title must not be empty")
    val title: String = "",

    @get:DecimalMin("0", message = "amount must be a positive value")
    val amount: BigDecimal = BigDecimal(0),

    val category: String? = "",

    @get:IsTransactionImportanceType(
        message = "importance must be one of SHOULD_NOT_HAVE, NICE_TO_HAVE, HAVE_TO_HAVE or ESSENTIAL"
    )
    val importance: TransactionsImportance = TransactionsImportance.BLANK,
)