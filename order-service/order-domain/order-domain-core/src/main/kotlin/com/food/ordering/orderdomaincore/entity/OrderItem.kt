package com.food.ordering.orderdomaincore.entity

import com.food.ordering.commondomain.entity.BaseEntity
import com.food.ordering.commondomain.valueobject.Money
import com.food.ordering.commondomain.valueobject.OrderId
import com.food.ordering.orderdomaincore.valueobject.OrderItemId

class OrderItem(
    var orderId: OrderId,
    val product: Product,
    val quantity: Int,
    val price: Money,
    val subTotal: Money,
): BaseEntity<OrderItemId>() {
    fun initializeOrderItem(orderId: OrderId, itemId: OrderItemId) {
        this.id = itemId
        this.orderId = orderId
    }

    fun isPriceValid(): Boolean {
        return price.isGreaterThanZero() && price == product.price && subTotal == price.times(quantity)
    }
}