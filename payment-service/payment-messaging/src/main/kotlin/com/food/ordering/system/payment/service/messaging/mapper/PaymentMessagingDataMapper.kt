package com.food.ordering.system.payment.service.messaging.mapper

import com.food.ordering.system.domain.valueobject.PaymentOrderStatus
import com.food.ordering.system.kafka.order.avro.model.PaymentRequestAvroModel
import com.food.ordering.system.kafka.order.avro.model.PaymentResponseAvroModel
import com.food.ordering.system.kafka.order.avro.model.PaymentStatus
import com.food.ordering.system.payment.service.domain.dto.PaymentRequest
import com.food.ordering.system.payment.service.domain.event.PaymentCancelledEvent
import com.food.ordering.system.payment.service.domain.event.PaymentCompletedEvent
import com.food.ordering.system.payment.service.domain.event.PaymentFailedEvent
import org.springframework.stereotype.Component
import java.util.*

@Component
class PaymentMessagingDataMapper {
    fun paymentCompletedEventToPaymentResponseAvroModel(
        paymentCompletedEvent: PaymentCompletedEvent
    ): PaymentResponseAvroModel {
        val payment = paymentCompletedEvent.payment
        return PaymentResponseAvroModel(
            id = UUID.randomUUID(),
            sagaId = UUID.randomUUID(),
            orderId = payment.orderId.value,
            customerId = payment.customerId.value,
            price = payment.price!!.amount,
            createdAt = payment.createdAt!!.toInstant(),
            paymentStatus = PaymentStatus.valueOf(payment.status!!.name),
            paymentId = payment.id.value,
            failureMessages = paymentCompletedEvent.failureMessages!!
        )
    }

    fun paymentCancelledEventToPaymentResponseAvroModel(
        paymentCancelledEvent: PaymentCancelledEvent
    ): PaymentResponseAvroModel {
        val payment = paymentCancelledEvent.payment
        return PaymentResponseAvroModel(
            id = UUID.randomUUID(),
            sagaId = UUID.randomUUID(),
            orderId = payment.orderId.value,
            customerId = payment.customerId.value,
            price = payment.price!!.amount,
            createdAt = payment.createdAt!!.toInstant(),
            paymentStatus = PaymentStatus.valueOf(payment.status!!.name),
            paymentId = payment.id.value,
            failureMessages = paymentCancelledEvent.failureMessages!!
        )
    }

    fun paymentFailedEventToPaymentResponseAvroModel(
        paymentFailedEvent: PaymentFailedEvent
    ): PaymentResponseAvroModel {
        val payment = paymentFailedEvent.payment
        return PaymentResponseAvroModel(
            id = UUID.randomUUID(),
            sagaId = UUID.randomUUID(),
            orderId = payment.orderId.value,
            customerId = payment.customerId.value,
            price = payment.price!!.amount,
            createdAt = payment.createdAt!!.toInstant(),
            paymentStatus = PaymentStatus.valueOf(payment.status!!.name),
            paymentId = payment.id.value,
            failureMessages = paymentFailedEvent.failureMessages!!
        )
    }

    fun paymentRequestAvroModelToPaymentRequest(
        paymentRequestAvroModel: PaymentRequestAvroModel
    ): PaymentRequest {
        return PaymentRequest(
            id = paymentRequestAvroModel.id.toString(),
            sagaId = paymentRequestAvroModel.sagaId.toString(),
            createdAt = paymentRequestAvroModel.createdAt,
            paymentOrderStatus = PaymentOrderStatus.valueOf(paymentRequestAvroModel.paymentOrderStatus.name),
            orderId = paymentRequestAvroModel.orderId.toString(),
            customerId = paymentRequestAvroModel.customerId.toString(),
            price = paymentRequestAvroModel.price,
        )
    }
}