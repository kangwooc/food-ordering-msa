package com.food.ordering.system.order.service.domain.entity

import com.food.ordering.system.domain.entity.AggregateRoot
import com.food.ordering.system.domain.valueobject.RestaurantId

class Restaurant(
    var products: List<Product>,
    var active: Boolean
): AggregateRoot<RestaurantId>() {
    fun isActive(): Boolean {
        return active
    }
}