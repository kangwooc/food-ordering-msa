package com.food.ordering.system.restaurant.service.dataaccess.restaurant.entity

import com.food.ordering.system.domain.valueobject.OrderApprovalStatus
import jakarta.persistence.*
import java.util.*

@Table(name = "order_approval", schema = "restaurant")
@Entity
class OrderApprovalEntity(
    @Id
    var id: UUID? = null,
    var restaurantId: UUID? = null,
    var orderId: UUID? = null,
    @Enumerated(EnumType.STRING)
    var status: OrderApprovalStatus? = null,
)