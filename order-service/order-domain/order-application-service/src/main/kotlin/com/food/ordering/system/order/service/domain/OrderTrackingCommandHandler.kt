package com.food.ordering.system.order.service.domain

import com.food.ordering.system.order.service.domain.dto.track.TrackOrderQuery
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse
import com.food.ordering.system.order.service.domain.exception.OrderNotFoundException
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository
import com.food.ordering.system.order.service.domain.valueobject.TrackingId
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class OrderTrackingCommandHandler(
    private val orderDataMapper: OrderDataMapper,
    private val orderRepository: OrderRepository,
) {
    val logger = LoggerFactory.getLogger(OrderTrackingCommandHandler::class.java)

    fun trackOrder(orderTrackingCommand: TrackOrderQuery): TrackOrderResponse {
        // Implementation for tracking order
        val orderResult = orderRepository.findByTrackingId(TrackingId(orderTrackingCommand.orderTrackingId))
        if (orderResult == null) {
            logger.error("Order with tracking id ${orderTrackingCommand.orderTrackingId} not found")
            throw OrderNotFoundException("Order with tracking id ${orderTrackingCommand.orderTrackingId} not found")
        }
        return orderDataMapper.orderToTrackOrderResponse(orderResult)
    }
}