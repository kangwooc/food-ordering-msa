package com.food.ordering.system.restaurant.service.domain

import com.food.ordering.system.restaurant.service.domain.dto.RestaurantApprovalRequest
import com.food.ordering.system.restaurant.service.domain.ports.input.message.listener.RestaurantApprovalRequestMessageListener
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class RestaurantApprovalRequestMessageListenerImpl(
    private val restaurantApprovalRequestHelper: RestaurantApprovalRequestHelper
): RestaurantApprovalRequestMessageListener {
    private val logger = LoggerFactory.getLogger(RestaurantApprovalRequestMessageListenerImpl::class.java)

    override fun approveOrder(restaurantApprovalRequest: RestaurantApprovalRequest) {
        val orderApprovalEvent = restaurantApprovalRequestHelper.persistOrderApproval(restaurantApprovalRequest)
        logger.info("Order approval process is completed for order ${restaurantApprovalRequest.orderId}")
        orderApprovalEvent.fire()
    }
}