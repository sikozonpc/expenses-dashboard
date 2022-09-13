package com.sikozonpc.expensesdashboard.util

import com.sikozonpc.expensesdashboard.transaction.TransactionRepository
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.reactive.server.WebTestClient

open class BaseTests : TestsContainerInitializer(){
    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var transactionRepository: TransactionRepository

    val fakeTransactions = createFakeTransactions()
}