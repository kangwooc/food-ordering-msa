package com.food.ordering.orderapplicationservice

import com.food.ordering.orderapplicationservice.dto.message.PaymentResponse
import com.food.ordering.orderapplicationservice.ports.input.message.listener.payment.PaymentResponseMessageListener
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Validated
@Service
class PaymentResponseMessageListenerImpl: PaymentResponseMessageListener {
    private val logger = LoggerFactory.getLogger(PaymentResponseMessageListenerImpl::class.java)
    override fun paymentCompleted(paymentResponse: PaymentResponse) {
        TODO("Not yet implemented")
    }

    override fun paymentCancelled(paymentResponse: PaymentResponse) {
        TODO("Not yet implemented")
    }
}