package com.food.ordering.system.saga

import com.food.ordering.system.domain.event.DomainEvent

interface SagaStep<T, S: DomainEvent<*>, U: DomainEvent<*>> {
    fun process(command: T): S
    fun rollback(command: T): U
}