package com.sikozonpc.expensesdashboard.transaction

import org.springframework.data.annotation.CreatedDate
import java.time.Instant
import javax.persistence.*

enum class TransactionCategories {
    WANT, NEED
}

@Entity()
data class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,

    val amount: String,

    val title: String,

    val category: String?,

    @Column(
        name = "created_date",
        nullable = false,
        updatable = false,
    )
    @CreatedDate
    val createdDate: Instant,
)