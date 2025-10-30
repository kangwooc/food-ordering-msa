package com.food.ordering.orderapplicationservice.ports.output.repository

import com.food.ordering.orderdomaincore.entity.Order
import com.food.ordering.system.order.service.domain.valueobject.TrackingId

interface OrderRepository {
    fun save(order: Order): Order?

    fun findByTrackingId(trackingId: com.food.ordering.system.order.service.domain.valueobject.TrackingId): Order?
}