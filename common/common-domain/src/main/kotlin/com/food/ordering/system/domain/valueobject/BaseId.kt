package com.food.ordering.system.domain.valueobject

abstract class BaseId<T> protected constructor(
    id: T
) {
    val value: T = id

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BaseId<*>

        return value == other.value
    }

    override fun hashCode(): Int {
        return value?.hashCode() ?: 0
    }
}