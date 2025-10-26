package com.food.ordering.orderapplicationservice.dto.create

import com.food.ordering.commondomain.valueobject.OrderStatus
import jakarta.validation.constraints.NotNull
import java.util.UUID

data class CreateOrderResponse(
    @field:NotNull
    val orderTrackingId: UUID,
    @field:NotNull
    val orderStatus: OrderStatus,
    @field:NotNull
    val message: String
)
