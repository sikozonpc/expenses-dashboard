package com.sikozonpc.expensesdashboard.dto

data class WebBFFDTO(
    val monthlyIncomes: List<MonthlyIncomeDTO> = listOf(),
    val transactions: List<TransactionDTO> = listOf(),
)