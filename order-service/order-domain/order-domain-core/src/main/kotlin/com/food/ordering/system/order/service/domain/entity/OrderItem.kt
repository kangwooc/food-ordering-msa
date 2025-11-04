package com.food.ordering.system.order.service.domain.entity

import com.food.ordering.system.domain.entity.BaseEntity
import com.food.ordering.system.domain.valueobject.OrderId
import com.food.ordering.system.order.service.domain.valueobject.OrderItemId

class OrderItem(
    var orderId: OrderId,
    val product: Product,
    val quantity: Int,
    val price: com.food.ordering.system.domain.valueobject.Money,
    val subTotal: com.food.ordering.system.domain.valueobject.Money,
): BaseEntity<OrderItemId>() {
    fun initializeOrderItem(orderId: OrderId, itemId: OrderItemId) {
        this.id = itemId
        this.orderId = orderId
    }

    fun isPriceValid(): Boolean {
        return price.isGreaterThanZero() && price == product.price && subTotal == price.times(quantity)
    }
}