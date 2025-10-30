package com.food.ordering.system.order.service.dataaccess.restaurant.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.*

@Table(name = "order_restaurant_m_view", schema = "restaurant")
@Entity
@IdClass(RestaurantEntityId::class)
class RestaurantEntity(
    @Id
    var restaurantId: UUID,
    @Id
    var productId: UUID,
    var restaurantName: String,
    var restaurantActive: Boolean,
    var productName: String,
    var productPrice: BigDecimal,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RestaurantEntity

        if (restaurantId != other.restaurantId) return false
        if (productId != other.productId) return false

        return true
    }

    override fun hashCode(): Int {
        return Objects.hash(restaurantId, productId)
    }
}