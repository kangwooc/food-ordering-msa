package com.food.ordering.system.restaurant.service.domain.entity

import com.food.ordering.system.domain.entity.BaseEntity
import com.food.ordering.system.domain.valueobject.Money
import com.food.ordering.system.domain.valueobject.OrderId
import com.food.ordering.system.domain.valueobject.OrderStatus

class OrderDetail(
    val orderStatus: OrderStatus? = null,
    val totalAmount: Money? = null,
    val products: List<Product>
): BaseEntity<OrderId>() {
}