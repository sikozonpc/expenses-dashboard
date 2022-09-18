package com.sikozonpc.expensesdashboard.service

import com.sikozonpc.expensesdashboard.expection.NotFoundException
import com.sikozonpc.expensesdashboard.entity.Transaction
import com.sikozonpc.expensesdashboard.dto.TransactionDTO
import com.sikozonpc.expensesdashboard.entity.TransactionImportance
import com.sikozonpc.expensesdashboard.repository.TransactionRepository
import mu.KLogging
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime

@Service
class TransactionService(
    val transactionRepository: TransactionRepository,
) {
    companion object : KLogging()

    fun getAll(): List<TransactionDTO> {
        return transactionRepository.findAll().map {
            TransactionDTO(it.id, it.title, it.amount, it.category)
        }
    }

    fun add(dto: TransactionDTO): TransactionDTO {
        val entity = Transaction(null, dto.amount, dto.title, dto.category, TransactionImportance.NICE_TO_HAVE, LocalDateTime.now())

        logger.info("Saving transaction $entity")

        return transactionRepository.save(entity).let {
            TransactionDTO(it.id, it.title, it.amount, it.category)
        }
    }

    fun delete(id: Int) = transactionRepository.deleteById(id)

    fun update(id: Int, updated: TransactionDTO): TransactionDTO {
        val transaction = transactionRepository.findById(id)
        return if (!transaction.isPresent) throw NotFoundException("transaction does not exist")
        else {
            transaction.get().let {
                it.amount = updated.amount ?: it.amount
                it.title = updated.title ?: it.title
                it.category = updated.category ?: it.category

                transactionRepository.save(it)

                TransactionDTO(
                    it.id,
                    it.title,
                    it.amount,
                    it.category,
                )
            }
        }

    }
}