package com.sikozonpc.expensesdashboard.annotation

import com.sikozonpc.expensesdashboard.entity.TransactionCategories
import enumContains
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY_GETTER)
@MustBeDocumented
@Constraint(validatedBy = [IsTransactionCategoryValidator::class])
annotation class IsTransactionCategory(
    val message: String = "{javax.validation.constraints.IsTransactionCategory.message}",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = [],
)

class IsTransactionCategoryValidator : ConstraintValidator<IsTransactionCategory, String> {
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (value?.isNullOrEmpty() == true) return true;
        return enumContains<TransactionCategories>(value as String);
    }
}
