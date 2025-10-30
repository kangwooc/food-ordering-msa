package com.food.ordering.system.order.service.domain.ports.input.message.listener.restaurantapproval

import com.food.ordering.system.order.service.domain.dto.message.RestaurantApprovalResponse

interface RestaurantApprovalMessageListener {
    fun orderApproved(approvalMessage: RestaurantApprovalResponse)
    fun orderRejected(approvalMessage: RestaurantApprovalResponse)
}