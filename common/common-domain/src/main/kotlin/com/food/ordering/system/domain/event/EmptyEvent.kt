package com.food.ordering.system.domain.event

class EmptyEvent private constructor(): DomainEvent<Unit> {
    companion object {
        val INSTANCE = EmptyEvent()
    }
    override fun fire() {
        // No operation
    }
}