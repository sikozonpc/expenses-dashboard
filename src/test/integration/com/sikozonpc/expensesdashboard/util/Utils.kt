package com.sikozonpc.expensesdashboard.util

import com.sikozonpc.expensesdashboard.entity.BudgetRule
import com.sikozonpc.expensesdashboard.entity.MonthlyIncome
import com.sikozonpc.expensesdashboard.entity.Transaction
import com.sikozonpc.expensesdashboard.entity.TransactionsImportance
import java.math.BigDecimal
import java.time.Clock
import java.time.Instant
import java.time.LocalDateTime

val NOW: LocalDateTime = LocalDateTime.now()

fun createFakeTransactions(): List<Transaction> {
    return listOf(
        Transaction(1, BigDecimal("500.69"), "Rent", "Housing", TransactionsImportance.ESSENTIAL, NOW),
        Transaction(2, BigDecimal("69.12"), "New cloths", "Shopping", TransactionsImportance.HAVE_TO_HAVE, NOW),
        Transaction(3, BigDecimal("4.72"), "Uber eats", "Eating out", TransactionsImportance.NICE_TO_HAVE, NOW)
    )
}

fun createFakeBudgetRules(): List<BudgetRule> {
    return listOf(
        BudgetRule(1, "20.00", "30.00", "0.00", "50.00", NOW),
        BudgetRule(2, "40.00", "30.00", "10.00", "20.00", NOW),
    )
}

fun createFakeMonthlyIncomes(): List<MonthlyIncome> = listOf(
    MonthlyIncome(1, "Salary", BigDecimal("2000"), true, NOW),
    MonthlyIncome(2, "Hobby", BigDecimal("100"), true, NOW)
)