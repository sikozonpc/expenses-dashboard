package com.sikozonpc.expensesdashboard.annotation

import com.sikozonpc.expensesdashboard.entity.TransactionsImportance
import enumContains
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY_GETTER)
@MustBeDocumented
@Constraint(validatedBy = [IsTransactionImportanceValidator::class])
annotation class IsTransactionImportanceType(
    val anyOf: Array<TransactionsImportance> = [],
    val message: String = "must be {anyOf}",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = [],
)

class IsTransactionImportanceValidator : ConstraintValidator<IsTransactionImportanceType, TransactionsImportance> {
    override fun isValid(value: TransactionsImportance?, context: ConstraintValidatorContext?): Boolean {
        if (value === TransactionsImportance.BLANK) return false
        return enumContains<TransactionsImportance>(value.toString())
    }

}
