package com.food.ordering.system.order.service.domain.ports.output.message.publisher.restaurantapproval

import com.food.ordering.commondomain.event.publisher.DomainEventPublisher
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent

interface OrderPaidRestaurantRequestMessagePublisher: DomainEventPublisher<com.food.ordering.system.order.service.domain.event.OrderPaidEvent> {
}