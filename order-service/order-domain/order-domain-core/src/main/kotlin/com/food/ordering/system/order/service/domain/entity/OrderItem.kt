package com.food.ordering.system.order.service.domain.entity

import com.food.ordering.commondomain.entity.BaseEntity
import com.food.ordering.commondomain.valueobject.Money
import com.food.ordering.commondomain.valueobject.OrderId
import com.food.ordering.system.order.service.domain.valueobject.OrderItemId

class OrderItem(
    var orderId: OrderId,
    val product: com.food.ordering.system.order.service.domain.entity.Product,
    val quantity: Int,
    val price: Money,
    val subTotal: Money,
): BaseEntity<com.food.ordering.system.order.service.domain.valueobject.OrderItemId>() {
    fun initializeOrderItem(orderId: OrderId, itemId: com.food.ordering.system.order.service.domain.valueobject.OrderItemId) {
        this.id = itemId
        this.orderId = orderId
    }

    fun isPriceValid(): Boolean {
        return price.isGreaterThanZero() && price == product.price && subTotal == price.times(quantity)
    }
}