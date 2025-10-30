package com.food.ordering.system.order.service.domain.valueobject

import java.util.UUID

class StreetAddress(
    val id: UUID,
    val street: String,
    val postalCode: String,
    val city: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as com.food.ordering.system.order.service.domain.valueobject.StreetAddress

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
