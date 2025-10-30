package com.food.ordering.system.order.service.domain.entity

import com.food.ordering.commondomain.entity.AggregateRoot
import com.food.ordering.commondomain.valueobject.RestaurantId

class Restaurant(
    var products: List<com.food.ordering.system.order.service.domain.entity.Product>,
    var active: Boolean
): AggregateRoot<RestaurantId>() {
    fun isActive(): Boolean {
        return active
    }
}