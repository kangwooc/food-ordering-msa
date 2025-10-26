package com.food.ordering.orderapplicationservice.ports.output.message.publisher.restaurantapproval

import com.food.ordering.commondomain.event.publisher.DomainEventPublisher
import com.food.ordering.orderdomaincore.event.OrderPaidEvent

interface OrderPaidRestaurantRequestMessagePublisher: DomainEventPublisher<OrderPaidEvent> {
}