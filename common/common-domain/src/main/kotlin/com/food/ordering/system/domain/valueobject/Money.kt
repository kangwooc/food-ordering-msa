package com.food.ordering.system.domain.valueobject

import java.math.BigDecimal
import java.math.RoundingMode

@JvmInline
value class Money(
    val amount: BigDecimal,
) {
    companion object {
        val ZERO = Money(BigDecimal.ZERO)
    }

    operator fun plus(other: Money): Money {
        return Money(setScale(this.amount + other.amount))
    }

    operator fun minus(other: Money): Money {
        return Money(setScale(this.amount - other.amount))
    }

    operator fun times(factor: Int): Money {
        return Money(setScale(this.amount.multiply(BigDecimal(factor))))
    }

    operator fun compareTo(other: Money): Int {
        return this.amount.compareTo(other.amount)
    }

    fun isGreaterThanZero(): Boolean {
        return this.amount > BigDecimal.ZERO
    }

    fun isGreaterThan(other: Money): Boolean {
        return this.amount > other.amount
    }

    private fun setScale(input: BigDecimal): BigDecimal {
        return input.setScale(2, RoundingMode.HALF_EVEN)
    }

    fun equalsMoney(other: Money): Boolean {
        return this.amount.compareTo(other.amount) == 0
    }
}