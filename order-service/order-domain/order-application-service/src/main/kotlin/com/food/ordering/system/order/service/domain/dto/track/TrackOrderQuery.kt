package com.food.ordering.system.order.service.domain.dto.track

import jakarta.validation.constraints.NotNull
import java.util.UUID

data class TrackOrderQuery(
    @field:NotNull
    val orderTrackingId: UUID,
)