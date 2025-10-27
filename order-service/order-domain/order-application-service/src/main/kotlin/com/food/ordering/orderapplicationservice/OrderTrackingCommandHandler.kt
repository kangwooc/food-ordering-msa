package com.food.ordering.orderapplicationservice

import com.food.ordering.commondomain.valueobject.OrderStatus
import com.food.ordering.orderapplicationservice.dto.track.TrackOrderQuery
import com.food.ordering.orderapplicationservice.dto.track.TrackOrderResponse
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