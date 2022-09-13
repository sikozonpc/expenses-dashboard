package com.sikozonpc.expensesdashboard.util

import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@Testcontainers
open class TestsContainerInitializer {
    companion object {
        @Container
        val mySQL = MySQLContainer<Nothing>(DockerImageName.parse("mysql:5.7")).apply {
            withDatabaseName("testdb")
            withUsername("myuser")
            withPassword("mypassword")
        }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", mySQL::getJdbcUrl)
            registry.add("spring.datasource.username", mySQL::getUsername)
            registry.add("spring.datasource.password", mySQL::getPassword)
        }
    }

}