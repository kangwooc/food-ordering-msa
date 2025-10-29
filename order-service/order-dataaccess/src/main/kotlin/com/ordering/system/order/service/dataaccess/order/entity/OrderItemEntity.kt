package com.ordering.system.order.service.dataaccess.order.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

@Table(name = "order_item")
@IdClass(OrderItemEntityId::class)
@Entity
class OrderItemEntity(
    @Id
    var id: UUID? = null,
    @Id
    @ManyToOne
    @JoinColumn(name = "ORDER_ID", nullable = false)
    var order: OrderEntity? = null,
    var productId: UUID,
    var price: BigDecimal,
    var quantity: Int,
    var subTotal: BigDecimal,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OrderItemEntity

        if (id != other.id) return false
        if (order != other.order) return false

        return true
    }

    override fun hashCode(): Int {
        return Objects.hash(id, order)
    }
}