package com.sikozonpc.expensesdashboard.controller

import com.sikozonpc.expensesdashboard.dto.TransactionDTO
import com.sikozonpc.expensesdashboard.entity.TransactionsImportance
import com.sikozonpc.expensesdashboard.util.BaseTests
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.data.repository.findByIdOrNull
import java.math.BigDecimal

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebClient
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TransactionControllerIntegrationTest : BaseTests() {
    @Test
    fun `should add a transaction`() {
        val payload = TransactionDTO(
            420,
            "Sample",
            BigDecimal(42.69),
            "Housing",
            TransactionsImportance.ESSENTIAL
        )

        val transaction = webTestClient.post()
            .uri(TransactionControllerURL)
            .bodyValue(payload)
            .exchange()
            .expectStatus().isCreated
            .expectBody<TransactionDTO>()
            .returnResult()
            .responseBody

        assertTrue {
            transaction!!.id != null             // makes sure it was created in the db
            transaction.id != 420
            transaction.category === "housing"   // test if it converts to lowercase
            transaction.importance === TransactionsImportance.ESSENTIAL
        }
    }

    @Test
    fun `should get all transactions`() {
        val transactions = webTestClient.get()
            .uri(TransactionControllerURL)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<List<TransactionDTO>>()
            .returnResult()
            .responseBody

        assertEquals(transactions!!.size, fakeTransactions!!.size)

        transactions.forEach {
            assertTrue { it.id != null }
        }
    }

    @Test
    fun `should update a transaction`() {
        val newAmount = BigDecimal("9999.99" )
        val newTitle = "new title"
        val newCategory = ""

        val transaction = transactionRepository.findAll().firstOrNull()
        val updated = TransactionDTO(transaction?.id, newTitle, newAmount, newCategory)

        webTestClient.put()
            .uri("$TransactionControllerURL/${transaction?.id}")
            .bodyValue(updated)
            .exchange()
            .expectStatus().isOk
            .expectBody<TransactionDTO>()
            .returnResult()
            .responseBody

        // fetching again to make sure it actually updated
        val updatedCourse = transactionRepository.findByIdOrNull(transaction!!.id!!)

        assertEquals(updatedCourse?.title, newTitle)
        assertEquals(updatedCourse?.amount, newAmount)
        assertEquals(updatedCourse?.category, newCategory)
    }

    @Test
    fun `should throw a 404 while trying to update a transaction that does not exist`() {
        val updated = TransactionDTO(422, "", BigDecimal("0"), "", TransactionsImportance.ESSENTIAL)

        webTestClient.put()
            .uri("$TransactionControllerURL/422")
            .bodyValue(updated)
            .exchange()
            .expectStatus().isNotFound
    }

    @Test
    fun `should delete a transaction`() {
        val transaction = transactionRepository.findAll().firstOrNull()
        webTestClient.delete()
            .uri("$TransactionControllerURL/${transaction!!.id}")
            .exchange()
            .expectStatus().isOk

        val deletedTransaction = transactionRepository.findById(transaction!!.id!!)
        assertFalse(deletedTransaction.isPresent)
    }
}