package com.food.ordering.system.domain.valueobject

import java.math.BigDecimal
import java.math.RoundingMode

@JvmInline
value class Money(
    val amount: BigDecimal,
) {
    companion object {
        val ZERO = com.food.ordering.system.domain.valueobject.Money(BigDecimal.ZERO)
    }

    operator fun plus(other: com.food.ordering.system.domain.valueobject.Money): com.food.ordering.system.domain.valueobject.Money {
        return com.food.ordering.system.domain.valueobject.Money(setScale(this.amount + other.amount))
    }

    operator fun minus(other: com.food.ordering.system.domain.valueobject.Money): com.food.ordering.system.domain.valueobject.Money {
        return com.food.ordering.system.domain.valueobject.Money(setScale(this.amount - other.amount))
    }

    operator fun times(factor: Int): com.food.ordering.system.domain.valueobject.Money {
        return com.food.ordering.system.domain.valueobject.Money(setScale(this.amount.multiply(BigDecimal(factor))))
    }

    operator fun compareTo(other: com.food.ordering.system.domain.valueobject.Money): Int {
        return this.amount.compareTo(other.amount)
    }

    fun isGreaterThanZero(): Boolean {
        return this.amount > BigDecimal.ZERO
    }

    fun isGreaterThan(other: com.food.ordering.system.domain.valueobject.Money): Boolean {
        return this.amount > other.amount
    }

    private fun setScale(input: BigDecimal): BigDecimal {
        return input.setScale(2, RoundingMode.HALF_EVEN)
    }
}