package com.sikozonpc.expensesdashboard.service

import com.sikozonpc.expensesdashboard.dto.SpendingTargetDTO
import com.sikozonpc.expensesdashboard.entity.Transaction
import com.sikozonpc.expensesdashboard.expection.NotFoundException
import com.sikozonpc.expensesdashboard.repository.BudgetRuleRepository
import com.sikozonpc.expensesdashboard.repository.MonthlyIncomeRepository
import com.sikozonpc.expensesdashboard.repository.TransactionRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.Calendar

@Service
class SpendingTargetService(
    private val transactionRepository: TransactionRepository,
    private val budgetRuleRepository: BudgetRuleRepository,
    private val monthlyIncomeRepository: MonthlyIncomeRepository,
) {
    // TODO: Need to revise month decrement since it doesn't make sense now
    fun calculate(monthDecrementWindow: Int = 0): List<SpendingTargetDTO> {
        val budgetRule = budgetRuleRepository.findAll().firstOrNull()
        if (budgetRule === null) throw NotFoundException("No budget rule not found")

        val monthlyIncomes = monthlyIncomeRepository.findAll().toList()
        if (monthlyIncomes.isEmpty()) throw NotFoundException("No monthly incomes found")

        val currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1

        val savingsPercentage =
            BigDecimal(100) - (budgetRule.wantsPercentage.toBigDecimal() + budgetRule.needsPercentage.toBigDecimal())

        val monthlyIncomesSum = monthlyIncomes
            .map { it.amount }
            .reduce { total, amount -> total + amount }

        val monthlySavingPercentage = (savingsPercentage * monthlyIncomesSum) / BigDecimal(100)

        val transactions = transactionRepository.findByMonthWindow(currentMonth - monthDecrementWindow, currentMonth)

        val monthlyNeedsSum = filterAndSumByCategory(transactions, "NEED")
        val monthlyWantsSum = filterAndSumByCategory(transactions, "WANT")
        val monthlySavingsSum = filterAndSumByCategory(transactions, "")

        return listOf(
            SpendingTargetDTO(
                monthlySavingPercentage,
                (monthlyIncomesSum + monthlySavingsSum) - monthlyNeedsSum - monthlyWantsSum,
                savingsPercentage,
                "#00A86B",
                "SAVINGS"
            ),
            SpendingTargetDTO(
                (budgetRule.wantsPercentage.toBigDecimal() * monthlyIncomesSum) / BigDecimal(100),
                monthlyWantsSum,
                BigDecimal(budgetRule.wantsPercentage),
                "#48AAAD",
                "WANTS"
            ),
            SpendingTargetDTO(
                (budgetRule.needsPercentage.toBigDecimal() * monthlyIncomesSum) / BigDecimal(100),
                monthlyNeedsSum,
                BigDecimal(budgetRule.needsPercentage),
                "#FDA172",
                "NEEDS"
            ),
        )
    }
}

fun filterAndSumByCategory(transactions: List<Transaction>, category: String): BigDecimal {
    val filteredByCategory = transactions
        .filter { it.category.equals(category) }
        .map { it.amount }
    if (filteredByCategory.isEmpty()) return BigDecimal(0)

    return filteredByCategory.reduce { total, amount -> total + amount }
}