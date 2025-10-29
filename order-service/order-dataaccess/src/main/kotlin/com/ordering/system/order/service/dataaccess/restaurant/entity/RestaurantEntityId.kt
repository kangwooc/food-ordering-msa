package com.ordering.system.order.service.dataaccess.restaurant.entity

import java.io.Serializable
import java.util.*

class RestaurantEntityId(
    val restaurantId: UUID,
    val productId: UUID
): Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}