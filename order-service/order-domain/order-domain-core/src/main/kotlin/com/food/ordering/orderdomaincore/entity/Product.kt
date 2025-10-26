package com.food.ordering.orderdomaincore.entity

import com.food.ordering.commondomain.entity.BaseEntity
import com.food.ordering.commondomain.valueobject.Money
import com.food.ordering.commondomain.valueobject.ProductId

class Product(
    val productId: ProductId,
    var name: String,
    var price: Money
): BaseEntity<ProductId>() {
    fun updateWithConfirmedNameAndPrice(name: String, price: Money) {
        this.name = name
        this.price = price
    }
}
