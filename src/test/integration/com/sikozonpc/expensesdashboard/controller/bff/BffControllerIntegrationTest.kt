package com.sikozonpc.expensesdashboard.controller.bff

import com.sikozonpc.expensesdashboard.dto.WebBFFDTO
import com.sikozonpc.expensesdashboard.util.BaseTests
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.expectBody

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebClient
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BffControllerIntegrationTest: BaseTests() {
    @Test
    fun `should compose and return correctly for Web`() {
        val webBFF = webTestClient.get()
            .uri(BFFControllerURL)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<WebBFFDTO>()
            .returnResult()
            .responseBody


    }
}