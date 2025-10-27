package com.food.ordering.orderapplicationservice.ports.output.repository

import com.food.ordering.orderdomaincore.entity.Order
import com.food.ordering.orderdomaincore.valueobject.TrackingId

interface OrderRepository {
    fun save(order: Order): Order?

    fun findByTrackingId(trackingId: TrackingId): Order?
}