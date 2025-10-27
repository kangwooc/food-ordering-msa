package com.food.ordering.orderapplicationservice

import com.food.ordering.commondomain.event.publisher.DomainEventPublisher
import com.food.ordering.orderdomaincore.event.OrderCreatedEvent
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.ApplicationEventPublisherAware
import org.springframework.stereotype.Component

@Component
class ApplicationDomainEventPublisher(
    private var applicationEventPublisher: ApplicationEventPublisher
): ApplicationEventPublisherAware, DomainEventPublisher<OrderCreatedEvent> {
    private val logger = LoggerFactory.getLogger(OrderCreateCommandHandler::class.java)

    override fun setApplicationEventPublisher(applicationEventPublisher: ApplicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher
    }

    override fun publish(domainEvent: OrderCreatedEvent) {
        this.applicationEventPublisher.publishEvent(domainEvent)
        logger.info("OrderCreatedEvent has been published for order id: ${domainEvent.order.id.value}")
    }
}