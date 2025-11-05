package com.food.ordering.system.payment.service.domain.entity

import com.food.ordering.system.domain.entity.AggregateRoot
import com.food.ordering.system.domain.valueobject.CustomerId
import com.food.ordering.system.domain.valueobject.Money
import com.food.ordering.system.domain.valueobject.OrderId
import com.food.ordering.system.domain.valueobject.PaymentStatus
import com.food.ordering.system.payment.service.domain.valueobject.PaymentId
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

class Payment(
    var orderId: OrderId,
    var customerId: CustomerId,
    var price: Money?,

    var paymentStatus: PaymentStatus? = null,
    var createdAt: ZonedDateTime? = null,
) : AggregateRoot<PaymentId>() {

    fun initializePayment() {
        this.id = PaymentId(UUID.randomUUID())
        createdAt = ZonedDateTime.now(ZoneId.of("UTC"))
    }

    fun validatePayment(failureMessages: MutableList<String>?) {
        if (price == null || !price!!.isGreaterThanZero()) {
            failureMessages?.add("Total price must be greater than zero!")
        }
    }

    fun updateStatus(paymentStatus: PaymentStatus) {
        this.paymentStatus = paymentStatus
    }
}