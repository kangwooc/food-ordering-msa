package com.food.ordering.system.kafka.order.avro.model

import kotlinx.serialization.Serializable

@Serializable
public enum class RestaurantOrderStatus {
    PAID,
}
