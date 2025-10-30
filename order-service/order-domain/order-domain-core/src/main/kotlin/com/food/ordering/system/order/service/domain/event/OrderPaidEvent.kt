package com.food.ordering.orderdomaincore.event

import com.food.ordering.commondomain.event.DomainEvent
import com.food.ordering.orderdomaincore.entity.Order
import java.time.ZonedDateTime

class OrderPaidEvent(
    order: Order,
    createdAt: ZonedDateTime
): OrderEvent(order, createdAt) {
}