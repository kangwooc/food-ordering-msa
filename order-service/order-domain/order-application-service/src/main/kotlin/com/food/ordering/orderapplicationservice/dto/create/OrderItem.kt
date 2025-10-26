package com.food.ordering.orderapplicationservice.dto.create

import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.util.UUID

data class OrderItem(
    @field:NotNull
    val productId: UUID,
    @field:NotNull
    val quantity: Int,
    @field:NotNull
    val price: BigDecimal,
    @field:NotNull
    val subTotal: BigDecimal
)
