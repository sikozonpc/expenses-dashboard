package com.sikozonpc.expensesdashboard.entity

import org.springframework.data.annotation.CreatedDate
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

enum class TransactionCategories {
    WANT, NEED
}

enum class TransactionImportance {
    SHOULD_NOT_HAVE,
    NICE_TO_HAVE,
    HAVE_TO_HAVE,
    ESSENTIAL,
}

@Entity()
data class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,

    var amount: BigDecimal,

    var title: String,

    var category: String?,

    @Enumerated()
    var importance: TransactionImportance,

    @Column(
        name = "created_date",
        nullable = false,
        updatable = false,
    )
    @CreatedDate
    val createdDate: LocalDateTime,
)