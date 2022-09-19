package com.sikozonpc.expensesdashboard.service

import com.sikozonpc.expensesdashboard.dto.MonthlyIncomeDTO
import com.sikozonpc.expensesdashboard.dto.TransactionDTO
import com.sikozonpc.expensesdashboard.dto.WebBFFDTO
import com.sikozonpc.expensesdashboard.repository.MonthlyIncomeRepository
import com.sikozonpc.expensesdashboard.repository.TransactionRepository
import org.springframework.stereotype.Service

@Service
class BFFService(
    private val transactionRepository: TransactionRepository,
    private val monthlyIncomeRepository: MonthlyIncomeRepository,
) {
    fun composeAndReturnForWeb(): WebBFFDTO {
        val transactions = transactionRepository.findAll()
        val monthlyIncomes = monthlyIncomeRepository.findAll().toList()

        return WebBFFDTO(
            monthlyIncomes.map { MonthlyIncomeDTO(it.id, it.title, it.amount, it.isRecurring) },
            transactions.map { TransactionDTO(it.id, it.title, it.amount, it.category, it.importance) },
        )
    }
}