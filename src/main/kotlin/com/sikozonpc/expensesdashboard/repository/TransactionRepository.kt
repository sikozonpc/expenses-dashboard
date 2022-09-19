package com.sikozonpc.expensesdashboard.repository

import com.sikozonpc.expensesdashboard.entity.Transaction
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface TransactionRepository : CrudRepository<Transaction, Int> {
    @Query(
        value = "SELECT * FROM transaction WHERE MONTH(created_at) >= ?1 AND MONTH(created_at) <= ?2",
        nativeQuery = true
    )
    fun findByMonthWindow(initial: Int = 0, final: Int = 0): List<Transaction>
}
