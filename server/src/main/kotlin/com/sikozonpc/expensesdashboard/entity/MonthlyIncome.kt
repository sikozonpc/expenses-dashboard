package com.sikozonpc.expensesdashboard.entity

import org.springframework.data.annotation.CreatedDate
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity()
data class MonthlyIncome(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,

    var title: String?,

    var amount: BigDecimal,

    var isRecurring: Boolean = true,

    @Column(
        nullable = false,
        updatable = false,
    )
    @CreatedDate
    val createdAt: LocalDateTime,
)