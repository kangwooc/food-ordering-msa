package com.food.ordering.system.order.service.domain

import com.food.ordering.system.order.service.domain.dto.track.TrackOrderQuery
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class OrderTrackCommandHandler(
    private val mapper: OrderDataMapper,
    private val orderRepository: OrderRepository
) {
    private val logger = org.slf4j.LoggerFactory.getLogger(com.food.ordering.system.order.service.domain.OrderTrackCommandHandler::class.java)

    @Transactional(readOnly = true)
    fun trackOrder(trackOrderQuery: TrackOrderQuery): TrackOrderResponse {
        val orderResult = orderRepository.findByTrackingId(
            com.food.ordering.system.order.service.domain.valueobject.TrackingId(
                trackOrderQuery.orderTrackingId
            )
        )

        if (orderResult == null) {
            logger.warn("Order with id ${trackOrderQuery.orderTrackingId} not found")
            throw com.food.ordering.system.order.service.domain.exception.OrderNotFoundException("Order with id ${trackOrderQuery.orderTrackingId} not found")
        }

        return mapper.orderToTrackOrderResponse(orderResult)
    }
}