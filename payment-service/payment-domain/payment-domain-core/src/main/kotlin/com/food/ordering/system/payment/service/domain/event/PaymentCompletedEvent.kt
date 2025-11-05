package com.food.ordering.system.payment.service.domain.event

import com.food.ordering.system.domain.event.publisher.DomainEventPublisher
import com.food.ordering.system.payment.service.domain.entity.Payment
import java.time.ZonedDateTime

class PaymentCompletedEvent(
    payment: Payment,
    createdAt: ZonedDateTime,
    private val paymentDomainEventPublisher: DomainEventPublisher<PaymentCompletedEvent>
): PaymentEvent(
    payment,
    createdAt,
    emptyList()
) {

    override fun fire() {
        paymentDomainEventPublisher.publish(this)
    }
}