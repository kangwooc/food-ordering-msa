package com.food.ordering.orderapplicationservice.ports.input.message.listener.restaurantapproval

import com.food.ordering.orderapplicationservice.dto.message.RestaurantApprovalResponse

interface RestaurantApprovalMessageListener {
    fun orderApproved(approvalMessage: RestaurantApprovalResponse)
    fun orderRejected(approvalMessage: RestaurantApprovalResponse)
}