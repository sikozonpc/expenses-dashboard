package com.sikozonpc.expensesdashboard.util

import com.sikozonpc.expensesdashboard.repository.BudgetRuleRepository
import com.sikozonpc.expensesdashboard.repository.MonthlyIncomeRepository
import com.sikozonpc.expensesdashboard.repository.TransactionRepository
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.reactive.server.WebTestClient

open class BaseTests : TestsContainerInitializer(){
    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var transactionRepository: TransactionRepository

    @Autowired
    lateinit var monthlyIncomeRepository: MonthlyIncomeRepository

    @Autowired
    lateinit var budgetRuleRepository: BudgetRuleRepository

    val fakeTransactions = createFakeTransactions()
    val fakeBudgetRules = createFakeBudgetRules()
    val fakeMonthlyIncomes = createFakeMonthlyIncomes()

    @BeforeEach
    fun setup() {
        transactionRepository.deleteAll()
        transactionRepository.saveAll(fakeTransactions)

        budgetRuleRepository.deleteAll()
        budgetRuleRepository.saveAll(fakeBudgetRules)

        monthlyIncomeRepository.deleteAll()
        monthlyIncomeRepository.saveAll(fakeMonthlyIncomes)
    }
}