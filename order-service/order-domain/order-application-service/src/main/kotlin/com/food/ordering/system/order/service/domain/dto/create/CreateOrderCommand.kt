package com.food.ordering.orderapplicationservice.dto.create

import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.util.UUID

data class CreateOrderCommand(
    @field:NotNull
    val customerId: UUID,
    @field:NotNull
    val restaurantId: UUID,
    @field:NotNull
    val price: BigDecimal,
    @field:NotNull
    val items: List<OrderItem>,
    @field:NotNull
    val address: OrderAddress
)