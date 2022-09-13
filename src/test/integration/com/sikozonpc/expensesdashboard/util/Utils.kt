package com.sikozonpc.expensesdashboard.util

import com.sikozonpc.expensesdashboard.transaction.Transaction
import java.time.Instant

val NOW: Instant = Instant.now()

fun createFakeTransactions(): List<Transaction> {
    return listOf(
        Transaction(1, "42.69", "Fake Transaction #1","WANT", NOW),
        Transaction(2, "69.12","Fake Transaction #2", "NEED", NOW),
        Transaction(3,  "0.69","Fake Transaction #3", "WANT", NOW)
    )
}