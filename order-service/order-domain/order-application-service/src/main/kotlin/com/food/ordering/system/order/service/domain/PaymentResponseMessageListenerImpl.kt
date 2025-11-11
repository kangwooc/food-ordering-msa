package com.food.ordering.system.order.service.domain

import com.food.ordering.system.order.service.domain.dto.message.PaymentResponse
import com.food.ordering.system.order.service.domain.entity.Order.Companion.FAILURE_MESSAGE_DELIMITER
import com.food.ordering.system.order.service.domain.ports.input.message.listener.payment.PaymentResponseMessageListener
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Validated
@Service
class PaymentResponseMessageListenerImpl(
    private val orderPaymentSaga: OrderPaymentSaga
) : PaymentResponseMessageListener {
    private val logger = LoggerFactory.getLogger(PaymentResponseMessageListenerImpl::class.java)
    override fun paymentCompleted(paymentResponse: PaymentResponse) {
        val orderPaidEvent = orderPaymentSaga.process(paymentResponse)
        logger.info("Publishing OrderPaidEvent is created for order id: ${paymentResponse.orderId}, event: $orderPaidEvent")
        orderPaidEvent.fire()
    }

    override fun paymentCancelled(paymentResponse: PaymentResponse) {
        orderPaymentSaga.rollback(paymentResponse)
        logger.info(
            "Payment rollback is completed for order id: ${paymentResponse.orderId}, rolling back the transaction : ${
                paymentResponse.failureMessages?.joinToString(
                    FAILURE_MESSAGE_DELIMITER
                )
            }"
        )
    }
}