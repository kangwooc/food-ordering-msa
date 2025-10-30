package com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment

import com.food.ordering.commondomain.event.publisher.DomainEventPublisher
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent

interface OrderCancelledPaymentRequestMessagePublisher: DomainEventPublisher<OrderCancelledEvent> {

}