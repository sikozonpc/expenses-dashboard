package com.sikozonpc.expensesdashboard.entity

import com.sikozonpc.expensesdashboard.dto.TransactionDTO
import org.springframework.data.annotation.CreatedDate
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

enum class TransactionsImportance {
    BLANK,
    /* Regretted expenses, it happens. */
    SHOULD_NOT_HAVE,

    // Expenses that you can live without but you wanted.
    NICE_TO_HAVE,

    // Expenses that your life would be way less enjoyable without.
    HAVE_TO_HAVE,

    // Essentials needs expenses.
    ESSENTIAL,
}

@Entity()
data class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Int?,

    var amount: BigDecimal,

    var title: String,

    var category: String?,

    @Enumerated(EnumType.STRING)
    var importance: TransactionsImportance,

    @Column(
        nullable = false,
        updatable = false,
    )
    @CreatedDate
    val createdAt: LocalDateTime,
)

fun Transaction?.toDTO(): TransactionDTO = TransactionDTO(
    this?.id,
    this?.title ?: "",
    this?.amount ?: BigDecimal(0),
    this?.category ?: "",
    this?.importance ?: TransactionsImportance.SHOULD_NOT_HAVE,
)