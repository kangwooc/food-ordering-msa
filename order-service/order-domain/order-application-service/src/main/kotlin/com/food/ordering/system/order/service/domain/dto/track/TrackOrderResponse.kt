package com.food.ordering.system.order.service.domain.dto.track

import com.food.ordering.commondomain.valueobject.OrderStatus
import jakarta.validation.constraints.NotNull
import java.util.UUID

data class TrackOrderResponse(
    @field:NotNull
    val orderTrackingId: UUID,
    @field:NotNull
    val orderStatus: OrderStatus,
    val failureMessages: List<String>?
)