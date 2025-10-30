package com.food.ordering.orderapplicationservice.ports.output.message.publisher.payment

import com.food.ordering.commondomain.event.publisher.DomainEventPublisher
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent

interface OrderCreatedPaymentRequestMessagePublisher: DomainEventPublisher<com.food.ordering.system.order.service.domain.event.OrderCreatedEvent> {

}