package com.food.ordering.system.order.service.domain

import com.food.ordering.system.order.service.domain.dto.message.RestaurantApprovalResponse
import com.food.ordering.system.order.service.domain.entity.Order.Companion.FAILURE_MESSAGE_DELIMITER
import com.food.ordering.system.order.service.domain.ports.input.message.listener.restaurantapproval.RestaurantApprovalMessageListener
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Validated
@Service
class RestaurantApprovalResponseMessageListenerImpl(
    private val orderApprovalSaga: OrderApprovalSaga,
): RestaurantApprovalMessageListener {
    private val logger = LoggerFactory.getLogger(PaymentResponseMessageListenerImpl::class.java)
    override fun orderApproved(approvalMessage: RestaurantApprovalResponse) {
        orderApprovalSaga.process(approvalMessage)
        logger.info("Order approval is completed for order id: ${approvalMessage.orderId}")
    }

    override fun orderRejected(approvalMessage: RestaurantApprovalResponse) {
        val orderCancelledEvent = orderApprovalSaga.rollback(approvalMessage)
        logger.info(
            "Order rejection is completed for order id: ${approvalMessage.orderId}, rolling back the transaction : ${
                approvalMessage.failureMessages?.joinToString(
                    FAILURE_MESSAGE_DELIMITER
                )
            }"
        )
        orderCancelledEvent.fire()
    }
}