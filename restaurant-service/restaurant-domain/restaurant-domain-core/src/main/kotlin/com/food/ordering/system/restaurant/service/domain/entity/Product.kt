package com.food.ordering.system.restaurant.service.domain.entity

import com.food.ordering.system.domain.entity.BaseEntity
import com.food.ordering.system.domain.valueobject.Money
import com.food.ordering.system.domain.valueobject.ProductId

class Product(
    var name: String,
    var price: Money,
    var quantity: Int,
    var available: Boolean,
): BaseEntity<ProductId>() {
    fun updateWithConfirmedNamePriceAndAvailability(
        name: String,
        price: Money,
        available: Boolean
    ) {
        this.name = name
        this.price = price
        this.available = available
    }
}