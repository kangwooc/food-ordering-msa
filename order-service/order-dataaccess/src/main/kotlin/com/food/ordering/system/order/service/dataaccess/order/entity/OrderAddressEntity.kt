package com.food.ordering.system.order.service.dataaccess.order.entity

import jakarta.persistence.*
import java.util.*

@Table(name = "order_address")
@Entity
class OrderAddressEntity(
    @Id
    var id: UUID,
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "ORDER_ID")
    var order: OrderEntity? = null,
    var street: String,
    var postalCode: String,
    var city: String,
)
