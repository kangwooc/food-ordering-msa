package com.food.ordering.orderapplicationservice

import com.food.ordering.orderapplicationservice.dto.track.TrackOrderQuery
import com.food.ordering.orderapplicationservice.dto.track.TrackOrderResponse
import com.food.ordering.orderapplicationservice.mapper.OrderDataMapper
import com.food.ordering.orderapplicationservice.ports.output.repository.OrderRepository
import com.food.ordering.orderdomaincore.entity.Order
import com.food.ordering.orderdomaincore.exception.OrderNotFoundException
import com.food.ordering.orderdomaincore.valueobject.TrackingId
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class OrderTrackCommandHandler(
    private val mapper: OrderDataMapper,
    private val orderRepository: OrderRepository,
) {
    private val logger = org.slf4j.LoggerFactory.getLogger(OrderTrackCommandHandler::class.java)

    @Transactional(readOnly = true)
    fun trackOrder(trackOrderQuery: TrackOrderQuery): TrackOrderResponse {
        val orderResult = orderRepository.findByTrackingId(TrackingId(trackOrderQuery.orderTrackingId))

        if (orderResult == null) {
            logger.warn("Order with id ${trackOrderQuery.orderTrackingId} not found")
            throw OrderNotFoundException("Order with id ${trackOrderQuery.orderTrackingId} not found")
        }

        return mapper.orderToTrackOrderResponse(orderResult)
    }
}