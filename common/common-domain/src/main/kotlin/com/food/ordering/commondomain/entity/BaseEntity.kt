package com.food.ordering.commondomain.entity

import java.util.*

abstract class BaseEntity<ID>{
    open var id: ID = null as ID

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as BaseEntity<*>

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }
}