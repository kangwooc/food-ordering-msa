package com.food.ordering.orderapplicationservice.ports.input.service

import com.food.ordering.orderapplicationservice.dto.create.CreateOrderCommand
import com.food.ordering.orderapplicationservice.dto.create.CreateOrderResponse
import com.food.ordering.orderapplicationservice.dto.track.TrackOrderQuery
import com.food.ordering.orderapplicationservice.dto.track.TrackOrderResponse
import jakarta.validation.Valid

interface OrderApplicationService {
    fun createOrder(@Valid command: CreateOrderCommand): CreateOrderResponse
    fun trackOrder(@Valid query: TrackOrderQuery): TrackOrderResponse
}