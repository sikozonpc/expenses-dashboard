package com.sikozonpc.expensesdashboard

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ExpensesDashboardApplication

fun main(args: Array<String>) {
	runApplication<ExpensesDashboardApplication>(*args)
}
