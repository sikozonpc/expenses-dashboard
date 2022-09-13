package com.sikozonpc.expensesdashboard.repository

import com.sikozonpc.expensesdashboard.transaction.TransactionRepository
import com.sikozonpc.expensesdashboard.util.TestsContainerInitializer
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TransactionRepositoryIntegrationTest : TestsContainerInitializer() {
    @Autowired
    lateinit var transactionRepository: TransactionRepository

    @BeforeEach
    fun setup() {

    }

}