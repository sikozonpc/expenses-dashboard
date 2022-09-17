package com.sikozonpc.expensesdashboard.entity

import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity()
data class BudgetRule(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,

    var needsPercentage: String,

    var wantsPercentage: String,

    @Column(
        name = "created_date",
        nullable = false,
        updatable = false,
    )
    @CreatedDate
    val createdDate: LocalDateTime,
)