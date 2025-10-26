package com.food.ordering.orderapplicationservice.ports.output.repository

import com.food.ordering.orderdomaincore.entity.Order

interface OrderRepository {
    fun save(order: Order): Order?

    fun findByTrackingId(trackingId: String): Order?
}