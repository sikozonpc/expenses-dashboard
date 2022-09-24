package com.sikozonpc.expensesdashboard.service

import com.sikozonpc.expensesdashboard.entity.BudgetRule
import com.sikozonpc.expensesdashboard.dto.BudgetRuleDTO
import com.sikozonpc.expensesdashboard.entity.toDTO
import com.sikozonpc.expensesdashboard.repository.BudgetRuleRepository
import mu.KLogging
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class BudgetRuleService(
    private val budgetRuleRepository: BudgetRuleRepository,
) {
    companion object : KLogging()

    fun getAll(): List<BudgetRuleDTO> {
        return budgetRuleRepository.findAll().map { it.toDTO() }
    }

    fun add(dto: BudgetRuleDTO): BudgetRuleDTO {
        val entity = BudgetRule(
            null,
            dto.essentialsGoal,
            dto.haveToHaveGoal,
            dto.niceToHaveGoal,
            dto.savingsGoal,
            LocalDateTime.now(),
        )

        logger.info("Saving budgetRule $entity")

        return budgetRuleRepository.save(entity).let { it.toDTO() }
    }

    fun deleteBydId(id: Int) = budgetRuleRepository.deleteById(id)
}