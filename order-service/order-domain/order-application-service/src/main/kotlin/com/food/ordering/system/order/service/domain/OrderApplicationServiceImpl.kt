package com.food.ordering.orderapplicationservice

import com.food.ordering.orderapplicationservice.dto.create.CreateOrderCommand
import com.food.ordering.orderapplicationservice.dto.create.CreateOrderResponse
import com.food.ordering.orderapplicationservice.dto.track.TrackOrderQuery
import com.food.ordering.orderapplicationservice.dto.track.TrackOrderResponse
import com.food.ordering.orderapplicationservice.ports.input.service.OrderApplicationService
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
class OrderApplicationServiceImpl(
    private val orderCreateCommandHandler: OrderCreateCommandHandler,
    private val orderTrackingCommandHandler: OrderTrackingCommandHandler
): OrderApplicationService {
    override fun createOrder(command: CreateOrderCommand): CreateOrderResponse {
        return orderCreateCommandHandler.createOrder(command)
    }

    override fun trackOrder(query: TrackOrderQuery): TrackOrderResponse {
        return orderTrackingCommandHandler.trackOrder(query)
    }
}