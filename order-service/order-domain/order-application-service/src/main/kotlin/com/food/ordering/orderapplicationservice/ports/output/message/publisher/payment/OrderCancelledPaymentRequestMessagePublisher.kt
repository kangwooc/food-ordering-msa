package com.food.ordering.orderapplicationservice.ports.output.message.publisher.payment

import com.food.ordering.commondomain.event.publisher.DomainEventPublisher
import com.food.ordering.orderdomaincore.event.OrderCancelledEvent
import com.food.ordering.orderdomaincore.event.OrderCreatedEvent

interface OrderCancelledPaymentRequestMessagePublisher: DomainEventPublisher<OrderCancelledEvent> {

}