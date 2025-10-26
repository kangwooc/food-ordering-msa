package com.food.ordering.orderdomaincore.event

import com.food.ordering.commondomain.event.DomainEvent
import com.food.ordering.orderdomaincore.entity.Order
import java.time.ZonedDateTime

abstract class OrderEvent(
    val order: Order,
    val createdAt: ZonedDateTime
): DomainEvent<Order> {
}