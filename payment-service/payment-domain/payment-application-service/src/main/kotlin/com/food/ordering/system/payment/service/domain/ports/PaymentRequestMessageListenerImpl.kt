package com.food.ordering.system.payment.service.domain.ports

import com.food.ordering.system.payment.service.domain.dto.PaymentRequest
import com.food.ordering.system.payment.service.domain.event.PaymentEvent
import com.food.ordering.system.payment.service.domain.ports.input.message.listener.PaymentRequestMessageListener
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class PaymentRequestMessageListenerImpl(
    private val paymentRequestHelper: PaymentRequestHelper,
) : PaymentRequestMessageListener {
    private val logger = LoggerFactory.getLogger(PaymentRequestMessageListenerImpl::class.java)

    override fun completePayment(paymentRequest: PaymentRequest) {
        val paymentEvent = paymentRequestHelper.persistPayment(paymentRequest)
        fireEvent(paymentEvent)
    }

    override fun cancelPayment(paymentRequest: PaymentRequest) {
        val paymentEvent = paymentRequestHelper.persistCancelPayment(paymentRequest)
        fireEvent(paymentEvent)
    }

    private fun fireEvent(paymentEvent: PaymentEvent) {
        logger.info("Payment request event published with payment id: ${paymentEvent.payment.id.value} and order id: ${paymentEvent.payment.orderId.value}")

        paymentEvent.fire()
    }
}