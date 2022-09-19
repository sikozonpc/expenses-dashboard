package com.sikozonpc.expensesdashboard.controller

import com.sikozonpc.expensesdashboard.dto.SpendingTargetDTO
import com.sikozonpc.expensesdashboard.entity.TransactionsImportance
import com.sikozonpc.expensesdashboard.service.filterAndSumByImportance
import com.sikozonpc.expensesdashboard.util.BaseTests
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.expectBody
import java.math.BigDecimal

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebClient
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SpendingTargetControllerIntegrationTest : BaseTests() {

    @Test
    fun `should return a 404 if no budget rule is found`() {
        monthlyIncomeRepository.deleteAll()

        webTestClient.get()
            .uri(SpendingTargetControllerURL)
            .exchange()
            .expectStatus().isNotFound
    }

    @Test
    fun `should return a 404 if no monthly incomes are found`() {
        budgetRuleRepository.deleteAll()

        webTestClient.get()
            .uri(SpendingTargetControllerURL)
            .exchange()
            .expectStatus().isNotFound
    }

    @Test
    fun `should correctly calculate the spending targets`() {
        val spendingTargets = webTestClient.get()
            .uri(SpendingTargetControllerURL)
            .exchange()
            .expectStatus().isOk
            .expectBody<List<SpendingTargetDTO>>()
            .returnResult()
            .responseBody

        val budgetRule = budgetRuleRepository.findAll().firstOrNull()!!
        val monthlyIncomesSum = monthlyIncomeRepository.findAll().toList()
            .map { it.amount }
            .reduce { total, amount -> total + amount }

        val (_, essentialsGoal, haveToHaveGoal, niceToHaveGoal, savingsGoal) = budgetRule

        val savingsPercentage = BigDecimal(savingsGoal)
        val monthlySavingsTotal = (savingsPercentage * monthlyIncomesSum) / BigDecimal("100")

        val monthlyShouldNotHave = filterAndSumByImportance(fakeTransactions, TransactionsImportance.SHOULD_NOT_HAVE)
        val monthlyNiceToHave = filterAndSumByImportance(fakeTransactions, TransactionsImportance.NICE_TO_HAVE)
        val monthlyHaveToHave = filterAndSumByImportance(fakeTransactions, TransactionsImportance.HAVE_TO_HAVE)
        val monthlyEssentials = filterAndSumByImportance(fakeTransactions, TransactionsImportance.ESSENTIAL)

        val allExpensesSum = monthlyEssentials - monthlyHaveToHave - monthlyNiceToHave - monthlyShouldNotHave

        assertEquals(spendingTargets!!.count(), 5)

        assertEquals(spendingTargets[0].category, "SAVINGS")
        assertEquals(spendingTargets[0].color, "#00A86B")
        assertEquals(spendingTargets[0].total, monthlySavingsTotal)
        assertEquals(spendingTargets[0].percentage, savingsPercentage)
        assertEquals(spendingTargets[0].current, monthlyIncomesSum - allExpensesSum)

        assertEquals(spendingTargets[1].category, "ESSENTIALS")
        assertEquals(spendingTargets[1].color, "#48AAAD")
        assertEquals(spendingTargets[1].total,
            (essentialsGoal.toBigDecimal() * monthlyIncomesSum) / BigDecimal(100))
        assertEquals(spendingTargets[1].percentage, BigDecimal(essentialsGoal))
        assertEquals(spendingTargets[1].current, monthlyEssentials)

        assertEquals(spendingTargets[2].category, "HAVE TO HAVE")
        assertEquals(spendingTargets[2].color, "#FDA172")
        assertEquals(spendingTargets[2].total,
            (haveToHaveGoal.toBigDecimal() * monthlyIncomesSum) / BigDecimal(100))
        assertEquals(spendingTargets[2].percentage, BigDecimal(haveToHaveGoal))
        assertEquals(spendingTargets[2].current, monthlyHaveToHave)

        assertEquals(spendingTargets[3].category, "NICE TO HAVE")
        assertEquals(spendingTargets[3].color, "#FDA172")
        assertEquals(spendingTargets[3].total,
            (niceToHaveGoal.toBigDecimal() * monthlyIncomesSum) / BigDecimal(100))
        assertEquals(spendingTargets[3].percentage, BigDecimal(niceToHaveGoal))
        assertEquals(spendingTargets[3].current, monthlyNiceToHave)

        assertEquals(spendingTargets[4].category, "SHOULD NOT HAVE")
        assertEquals(spendingTargets[4].color, "RED")
        assertEquals(spendingTargets[4].total, BigDecimal(0))
        assertEquals(spendingTargets[4].percentage, BigDecimal(0))
        assertEquals(spendingTargets[4].current, monthlyShouldNotHave)
    }

    @Test
    fun `should only allow "monthDecrement" value to be positive values`() {
    }
}
