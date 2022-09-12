package com.sikozonpc.expensesdashboard.transaction

import mu.KLogging
import org.springframework.stereotype.Service
import java.time.Instant

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
        val entity = Transaction(null, dto.amount, dto.title, dto.category, Instant.now())

        logger.info("Saving transaction $entity")

        return transactionRepository.save(entity).let {
            TransactionDTO(it.id, it.title, it.amount, it.category)
        }
    }
}