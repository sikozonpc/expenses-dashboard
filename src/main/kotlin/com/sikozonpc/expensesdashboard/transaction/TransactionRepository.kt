package com.sikozonpc.expensesdashboard.transaction

import org.springframework.data.repository.CrudRepository

interface TransactionRepository : CrudRepository<Transaction, Int>
