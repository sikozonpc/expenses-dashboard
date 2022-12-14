package com.sikozonpc.expensesdashboard.service

import com.sikozonpc.expensesdashboard.dto.SpendingTargetDTO
import com.sikozonpc.expensesdashboard.entity.Transaction
import com.sikozonpc.expensesdashboard.entity.TransactionsImportance
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

        val (_, essentialsGoal, haveToHaveGoal, niceToHaveGoal, savingsGoal) = budgetRule

        val savingsPercentage = BigDecimal(savingsGoal)

        val monthlyIncomesSum = monthlyIncomes
            .map { it.amount }
            .reduce { total, amount -> total + amount }

        val monthlySavingGoalTotal = (savingsPercentage * monthlyIncomesSum) / BigDecimal(100)

        val transactions = transactionRepository.findByMonthWindow(currentMonth - monthDecrementWindow, currentMonth)

        val monthlyShouldNotHave = filterAndSumByImportance(transactions, TransactionsImportance.SHOULD_NOT_HAVE)
        val monthlyNiceToHave = filterAndSumByImportance(transactions, TransactionsImportance.NICE_TO_HAVE)
        val monthlyHaveToHave = filterAndSumByImportance(transactions, TransactionsImportance.HAVE_TO_HAVE)
        val monthlyEssentials = filterAndSumByImportance(transactions, TransactionsImportance.ESSENTIAL)

        val allExpensesSum = monthlyEssentials + monthlyHaveToHave + monthlyNiceToHave + monthlyShouldNotHave
        val essentialsTotal = (essentialsGoal.toBigDecimal() * monthlyIncomesSum) / BigDecimal(100)
        val haveToHaveTotal = (haveToHaveGoal.toBigDecimal() * monthlyIncomesSum) / BigDecimal(100)
        val niceToHaveTotal = (niceToHaveGoal.toBigDecimal() * monthlyIncomesSum) / BigDecimal(100)

        return listOf(
            SpendingTargetDTO(
                monthlySavingGoalTotal,
                monthlyIncomesSum - allExpensesSum,
                savingsPercentage,
                "#00A86B",
                "SAVINGS"
            ),
            SpendingTargetDTO(
                essentialsTotal,
                monthlyEssentials,
                BigDecimal(essentialsGoal),
                "#48AAAD",
                "ESSENTIALS"
            ),
            SpendingTargetDTO(
                haveToHaveTotal,
                monthlyHaveToHave,
                BigDecimal(haveToHaveGoal),
                "#FDA172",
                "HAVE_TO_HAVE"
            ),
            SpendingTargetDTO(
                niceToHaveTotal,
                monthlyNiceToHave,
                BigDecimal(niceToHaveGoal),
                "#FDA172",
                "NICE_TO_HAVE"
            ),
            SpendingTargetDTO(
                BigDecimal(0), // TODO: This might need recalculation based on the sum of the above importances
                monthlyShouldNotHave,
                BigDecimal(0),
                "RED",
                "SHOULD_NOT_HAVE"
            ),
        )
    }
}


fun filterAndSumByImportance(transactions: List<Transaction>, importance: TransactionsImportance): BigDecimal {
    val importanceString =  importance.name.lowercase()

    val filteredByImportance = transactions
        .filter { it.importance.name.lowercase() == importanceString }
        .map { it.amount }
    if (filteredByImportance.isEmpty()) return BigDecimal(0)

    return filteredByImportance.reduce { total, amount -> total + amount }
}