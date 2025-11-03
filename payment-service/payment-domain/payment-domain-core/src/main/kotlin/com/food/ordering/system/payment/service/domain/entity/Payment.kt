package com.food.ordering.system.payment.service.domain.entity

import com.food.ordering.commondomain.entity.AggregateRoot
import com.food.ordering.commondomain.valueobject.CustomerId
import com.food.ordering.commondomain.valueobject.Money
import com.food.ordering.commondomain.valueobject.OrderId
import com.food.ordering.commondomain.valueobject.PaymentStatus
import com.food.ordering.system.payment.service.domain.valueobject.PaymentId
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

class Payment(
    var orderId: OrderId,
    var customerId: CustomerId,
    var price: Money?,

    var paymentStatus: PaymentStatus,
    private var createdAt: ZonedDateTime,
    ) : AggregateRoot<PaymentId>() {

    fun initializePayment() {
        this.id = PaymentId(UUID.randomUUID())
        createdAt = ZonedDateTime.now(ZoneId.of("UTC"))
    }

    fun validatePayment() {
        if (price == null || !price!!.isGreaterThanZero()) {
            throw IllegalArgumentException("Payment price must be greater than zero.")
        }
    }

    fun updateStatus(paymentStatus: PaymentStatus) {
        this.paymentStatus = paymentStatus
    }
}