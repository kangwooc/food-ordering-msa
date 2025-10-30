package com.food.ordering.orderapplicationservice.ports.input.message.listener.payment

import com.food.ordering.orderapplicationservice.dto.message.PaymentResponse

interface PaymentResponseMessageListener {
    fun paymentCompleted(paymentResponse: PaymentResponse)
    fun paymentCancelled(paymentResponse: PaymentResponse)
}