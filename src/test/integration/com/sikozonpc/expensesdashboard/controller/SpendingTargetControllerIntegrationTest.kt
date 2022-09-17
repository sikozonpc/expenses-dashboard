package com.sikozonpc.expensesdashboard.controller

import com.sikozonpc.expensesdashboard.dto.SpendingTargetDTO
import com.sikozonpc.expensesdashboard.entity.Transaction
import com.sikozonpc.expensesdashboard.service.filterAndSumByCategory
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

        val savingsPercentage =
            BigDecimal(100) - (budgetRule.wantsPercentage.toBigDecimal() + budgetRule.needsPercentage.toBigDecimal())

        val monthlyNeedsSum = filterAndSumByCategory(fakeTransactions, "NEED")
        val monthlyWantsSum = filterAndSumByCategory(fakeTransactions, "WANT")
        val monthlySavingsSum = filterAndSumByCategory(fakeTransactions, "")

        assertEquals(spendingTargets!!.count(), 3)

        assertEquals(spendingTargets[0].category, "SAVINGS")
        assertEquals(spendingTargets[0].color, "#00A86B")
        assertEquals(spendingTargets[0].total, (savingsPercentage * monthlyIncomesSum) / BigDecimal(100))
        assertEquals(spendingTargets[0].percentage, savingsPercentage)
        assertEquals(spendingTargets[0].current,
            (monthlyIncomesSum + monthlySavingsSum) - monthlyNeedsSum - monthlyWantsSum)

        assertEquals(spendingTargets[1].category, "WANTS")
        assertEquals(spendingTargets[1].color, "#48AAAD")
        assertEquals(spendingTargets[1].total,
            (budgetRule.wantsPercentage.toBigDecimal() * monthlyIncomesSum) / BigDecimal(100))
        assertEquals(spendingTargets[1].percentage, BigDecimal(budgetRule.wantsPercentage))
        assertEquals(spendingTargets[1].current, monthlyWantsSum)

        assertEquals(spendingTargets[2].category, "NEEDS")
        assertEquals(spendingTargets[2].color, "#FDA172")
        assertEquals(spendingTargets[2].total,
            (budgetRule.needsPercentage.toBigDecimal() * monthlyIncomesSum) / BigDecimal(100))
        assertEquals(spendingTargets[2].percentage, BigDecimal(budgetRule.needsPercentage))
        assertEquals(spendingTargets[2].current, monthlyNeedsSum)
    }

    @Test
    fun `should only allow "monthDecrement" value to be positive values`() {
    }
}
