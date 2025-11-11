package com.food.ordering.system.restaurant.service.domain.entity

import com.food.ordering.system.domain.entity.AggregateRoot
import com.food.ordering.system.domain.valueobject.Money
import com.food.ordering.system.domain.valueobject.OrderStatus
import com.food.ordering.system.domain.valueobject.RestaurantId

class Restaurant(
    var orderApproval: OrderApproval,
    var active: Boolean,
    var orderDetail: OrderDetail,
): AggregateRoot<RestaurantId>() {
    fun validateOrder(failureMessages: MutableList<String>) {
        if (orderDetail.orderStatus != OrderStatus.PAID) {
            failureMessages.add("Payment not completed for order id: ${orderDetail.id.value}")
        }

        val totalAmount = orderDetail.products
            .map {
                if (!it.available) {
                    failureMessages.add("Product ${it.id.value} is not available")
                }
                it.price.amount.times(it.quantity.toBigDecimal())
            }
            .fold(Money.ZERO) { a, b -> a.plus(Money(b)) }

        if (orderDetail.totalAmount != totalAmount) {
            failureMessages.add(
                "Total amount: ${orderDetail.totalAmount?.amount} is not equal to sum of product prices: ${totalAmount.amount} " +
                        "for order id: ${orderDetail.id.value}"
            )
        }
    }

    fun constructOrderApproval(orderApproval: OrderApproval) {
        this.orderApproval = OrderApproval(
            restaurantId = this.id,
            orderId = orderApproval.orderId,
            orderApprovalStatus = orderApproval.orderApprovalStatus
        )
    }
}