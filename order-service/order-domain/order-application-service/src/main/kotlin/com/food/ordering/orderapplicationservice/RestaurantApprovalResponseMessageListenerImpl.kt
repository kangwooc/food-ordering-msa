package com.food.ordering.orderapplicationservice

import com.food.ordering.orderapplicationservice.dto.message.RestaurantApprovalResponse
import com.food.ordering.orderapplicationservice.ports.input.message.listener.restaurantapproval.RestaurantApprovalMessageListener
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Validated
@Service
class RestaurantApprovalResponseMessageListenerImpl: RestaurantApprovalMessageListener {
    private val logger = LoggerFactory.getLogger(PaymentResponseMessageListenerImpl::class.java)
    override fun orderApproved(approvalMessage: RestaurantApprovalResponse) {
        TODO("Not yet implemented")
    }

    override fun orderRejected(approvalMessage: RestaurantApprovalResponse) {
        TODO("Not yet implemented")
    }


}