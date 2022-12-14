package com.sikozonpc.expensesdashboard.service

import com.sikozonpc.expensesdashboard.expection.NotFoundException
import com.sikozonpc.expensesdashboard.entity.Transaction
import com.sikozonpc.expensesdashboard.dto.TransactionDTO
import com.sikozonpc.expensesdashboard.entity.toDTO
import com.sikozonpc.expensesdashboard.repository.TransactionRepository
import mu.KLogging
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class TransactionService(
    val transactionRepository: TransactionRepository,
) {
    companion object : KLogging()

    fun getAll(): List<TransactionDTO> {
        return transactionRepository.findAll().map { it.toDTO() }
    }

    fun add(dto: TransactionDTO): TransactionDTO {
        val entity = Transaction(
            null,
            dto.amount,
            dto.title,
            dto.category?.lowercase(),
            dto.importance,
            LocalDateTime.now(),
        )

        logger.info("Saving transaction $entity")

        return transactionRepository.save(entity).let { it.toDTO() }
    }

    fun delete(id: Int) = transactionRepository.deleteById(id)

    fun update(id: Int, updated: TransactionDTO): TransactionDTO {
        val transaction = transactionRepository.findById(id)
        return if (!transaction.isPresent) throw NotFoundException("transaction does not exist")
        else {
            transaction.get().let {
                it.amount = updated.amount ?: it.amount
                it.title = updated.title ?: it.title
                it.category = updated.category?.lowercase() ?: it.category?.lowercase()
                it.importance = updated.importance ?: it.importance

                transactionRepository.save(it)

                it.toDTO()
            }
        }

    }

    fun getAllByMonthDecrement(month: Int): List<TransactionDTO> {
        val currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1

        return transactionRepository
            .findByMonthWindow(currentMonth - month, currentMonth)
            .map { it.toDTO() }
    }
}