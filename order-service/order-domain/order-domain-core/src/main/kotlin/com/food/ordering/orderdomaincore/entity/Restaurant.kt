package com.food.ordering.orderdomaincore.entity

import com.food.ordering.commondomain.entity.AggregateRoot
import com.food.ordering.commondomain.valueobject.RestaurantId

class Restaurant(
    var products: List<Product>,
    var active: Boolean
): AggregateRoot<RestaurantId>() {
    fun isActive(): Boolean {
        return active
    }
}