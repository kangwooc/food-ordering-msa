package com.ordering.system.order.service.dataaccess.order.entity

import com.food.ordering.commondomain.valueobject.OrderStatus
import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

@Table(name = "orders")
@Entity
class OrderEntity(
    @Id
    var id: UUID,
    var customerId: UUID,
    var restaurantId: UUID,
    var trackingId: UUID,
    var price: BigDecimal,
    @Enumerated(EnumType.STRING)
    var status: OrderStatus,
    var failureMessages: String?,

    @OneToOne(mappedBy = "order", cascade = [CascadeType.ALL])
    var address: OrderAddressEntity,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    var items: MutableList<OrderItemEntity>,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OrderEntity

        return id == other.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }
}