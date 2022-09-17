package com.sikozonpc.expensesdashboard.controller

import com.sikozonpc.expensesdashboard.dto.BudgetRuleDTO
import com.sikozonpc.expensesdashboard.util.BaseTests
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.expectBody
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebClient
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BudgetRuleControllerIntegrationTest : BaseTests() {
    @Test
    fun `should add a budget rule`() {
        val payload = BudgetRuleDTO(420, "20.9", "60.0")

        val budgetRule = webTestClient.post()
            .uri(BudgetRuleControllerURL)
            .bodyValue(payload)
            .exchange()
            .expectStatus().isCreated
            .expectBody<BudgetRuleDTO>()
            .returnResult()
            .responseBody

        assertTrue {
            // makes sure it was created in the db
            budgetRule!!.id != null
            budgetRule.id != 420
        }
    }

    @Test
    fun `should get all budget rules`() {
        val budgetRules = webTestClient.get()
            .uri(BudgetRuleControllerURL)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<List<BudgetRuleDTO>>()
            .returnResult()
            .responseBody

        assertEquals(budgetRules!!.size, fakeBudgetRules!!.size)

        budgetRules.forEach {
            assertTrue { it.id != null }
        }
    }

    @Test
    fun `should delete a budget rule`() {
        val budgetRule = budgetRuleRepository.findAll().firstOrNull()
        webTestClient.delete()
            .uri("$BudgetRuleControllerURL/${budgetRule!!.id}")
            .exchange()
            .expectStatus().isOk
    }
}