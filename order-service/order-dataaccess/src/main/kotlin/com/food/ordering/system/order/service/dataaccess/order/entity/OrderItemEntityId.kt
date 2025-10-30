package com.food.ordering.system.order.service.dataaccess.order.entity

import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.io.Serializable
import java.util.*

class OrderItemEntityId(
    @Id
    var id: UUID? = null,
    @Id
    @ManyToOne
    @JoinColumn(name = "ORDER_ID", nullable = false)
    var order: OrderEntity,
): Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OrderItemEntityId

        if (id != other.id) return false
        if (order != other.order) return false

        return true
    }

    override fun hashCode(): Int {
        return Objects.hash(id, order)
    }
}