package com.food.ordering.system.restaurant.service.domain.entity

import com.food.ordering.system.domain.entity.BaseEntity
import com.food.ordering.system.domain.valueobject.OrderApprovalStatus
import com.food.ordering.system.domain.valueobject.OrderId
import com.food.ordering.system.domain.valueobject.RestaurantId
import com.food.ordering.system.restaurant.service.domain.valueobject.OrderApprovalID

class OrderApproval(
    var restaurantId: RestaurantId,
    var orderId: OrderId,
    var orderApprovalStatus: OrderApprovalStatus? = null,
): BaseEntity<OrderApprovalID>() {
}