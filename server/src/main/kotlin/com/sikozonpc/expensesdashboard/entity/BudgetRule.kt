package com.sikozonpc.expensesdashboard.entity

import com.sikozonpc.expensesdashboard.dto.BudgetRuleDTO
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity()
data class BudgetRule(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,

    var essentialsGoal: String,

    var haveToHaveGoal: String,

    var niceToHaveGoal: String,

    var savingsGoal: String,

    @Column(
        nullable = false,
        updatable = false,
    )
    @CreatedDate
    val createdAt: LocalDateTime,
)

fun BudgetRule?.toDTO(): BudgetRuleDTO = BudgetRuleDTO(
    this?.id,
    this?.essentialsGoal ?: "",
    this?.haveToHaveGoal ?: "",
    this?.niceToHaveGoal ?: "",
    this?.savingsGoal ?: "",
)