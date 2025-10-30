package com.food.ordering.system.order.service.domain.ports.input.service

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderQuery
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse
import jakarta.validation.Valid

interface OrderApplicationService {
    fun createOrder(@Valid command: CreateOrderCommand): CreateOrderResponse
    fun trackOrder(@Valid query: TrackOrderQuery): TrackOrderResponse
}