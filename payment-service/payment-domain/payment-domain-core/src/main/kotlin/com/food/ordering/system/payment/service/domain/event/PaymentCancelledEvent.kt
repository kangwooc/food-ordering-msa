package com.food.ordering.system.payment.service.domain.event

import com.food.ordering.system.domain.event.publisher.DomainEventPublisher
import com.food.ordering.system.payment.service.domain.entity.Payment
import java.time.ZonedDateTime

class PaymentCancelledEvent(
    payment: Payment,
    createdAt: ZonedDateTime,
    private val paymentCancelledEventPublisher: DomainEventPublisher<PaymentCancelledEvent>,
): PaymentEvent(
    payment,
    createdAt,
    emptyList()
) {
    override fun fire() {
        paymentCancelledEventPublisher.publish(this)
    }
}