package com.food.ordering.commondomain.event.publisher

import com.food.ordering.commondomain.event.DomainEvent

interface DomainEventPublisher<T: DomainEvent<*>> {
    fun publish(domainEvent: T)
}