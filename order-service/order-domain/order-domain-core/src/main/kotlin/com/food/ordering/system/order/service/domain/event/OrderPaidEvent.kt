package com.food.ordering.system.order.service.domain.event

import com.food.ordering.system.domain.event.publisher.DomainEventPublisher
import com.food.ordering.system.order.service.domain.entity.Order
import java.time.ZonedDateTime

class OrderPaidEvent(
    order: Order,
    createdAt: ZonedDateTime,
    private val orderPaidEventDomainEventPublisher: DomainEventPublisher<OrderPaidEvent>,
): OrderEvent(order, createdAt) {
    override fun fire() {
        orderPaidEventDomainEventPublisher.publish(this)
    }
}