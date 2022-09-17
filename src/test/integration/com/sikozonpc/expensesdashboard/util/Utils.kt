package com.sikozonpc.expensesdashboard.util

import com.sikozonpc.expensesdashboard.entity.BudgetRule
import com.sikozonpc.expensesdashboard.entity.MonthlyIncome
import com.sikozonpc.expensesdashboard.entity.Transaction
import java.time.Clock
import java.time.Instant
import java.time.LocalDateTime

val NOW: LocalDateTime = LocalDateTime.now()

fun createFakeTransactions(): List<Transaction> {
    return listOf(
        Transaction(1, "42.69", "Fake Transaction #1","WANT", NOW),
        Transaction(2, "69.12","Fake Transaction #2", "NEED", NOW),
        Transaction(3,  "0.69","Fake Transaction #3", "WANT", NOW)
    )
}

fun createFakeBudgetRules(): List<BudgetRule> {
    return listOf(
        BudgetRule(1, "20.00", "30.00", NOW),
        BudgetRule(2, "40.00", "10.00", NOW),
    )
}

fun createFakeMonthlyIncomes(): List<MonthlyIncome> = listOf(
    MonthlyIncome(1, "Salary", "2000", true, NOW),
    MonthlyIncome(2, "Hobby", "100", true, NOW)
)