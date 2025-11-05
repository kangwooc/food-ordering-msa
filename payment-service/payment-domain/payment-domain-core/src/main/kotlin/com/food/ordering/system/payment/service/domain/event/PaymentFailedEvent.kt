package com.food.ordering.system.payment.service.domain.event

import com.food.ordering.system.domain.event.publisher.DomainEventPublisher
import com.food.ordering.system.payment.service.domain.entity.Payment
import java.time.ZonedDateTime

class PaymentFailedEvent(
    payment: Payment,
    createdAt: ZonedDateTime,
    private val paymentFailedDomainEventPublisher: DomainEventPublisher<PaymentFailedEvent>,
): PaymentEvent(
    payment,
    createdAt,
    emptyList(),
) {
    override fun fire() {
        paymentFailedDomainEventPublisher.publish(this)
    }
}