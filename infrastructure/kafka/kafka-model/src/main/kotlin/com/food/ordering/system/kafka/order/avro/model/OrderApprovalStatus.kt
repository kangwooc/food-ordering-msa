package com.food.ordering.system.kafka.order.avro.model

import kotlinx.serialization.Serializable

@Serializable
enum class OrderApprovalStatus {
    APPROVED,
    REJECTED,
}
