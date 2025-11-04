package com.food.ordering.system.order.service.domain

import com.food.ordering.system.domain.valueobject.OrderStatus
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderQuery
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class OrderTrackingCommandHandler {
    val logger = LoggerFactory.getLogger(OrderTrackingCommandHandler::class.java)

    fun trackOrder(orderTrackingCommand: TrackOrderQuery): TrackOrderResponse {
        // Implementation for tracking order
        return TrackOrderResponse(
            orderTrackingId = orderTrackingCommand.orderTrackingId,
            orderStatus = OrderStatus.PENDING,
            failureMessages = emptyList()
        )
    }
}