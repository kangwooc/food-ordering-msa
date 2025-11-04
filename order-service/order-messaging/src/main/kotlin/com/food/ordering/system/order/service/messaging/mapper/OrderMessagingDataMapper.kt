package com.food.ordering.system.order.service.messaging.mapper

import com.food.ordering.system.domain.valueobject.OrderApprovalStatus
import com.food.ordering.system.domain.valueobject.PaymentStatus
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent
import com.food.ordering.system.kafka.order.avro.model.*
import com.food.ordering.system.order.service.domain.dto.message.PaymentResponse
import com.food.ordering.system.order.service.domain.dto.message.RestaurantApprovalResponse
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent
import org.springframework.stereotype.Component
import java.util.*

@Component
class OrderMessagingDataMapper {
    fun orderCreatedEventToOrderResponse(orderCreatedEvent: OrderCreatedEvent): PaymentRequestAvroModel {
        val order = orderCreatedEvent.order
        return PaymentRequestAvroModel(
            id = UUID.randomUUID(),
            orderId = order.id.value,
            sagaId = UUID.randomUUID(),
            customerId = order.customerId.value,
            price = order.price.amount,
            createdAt = orderCreatedEvent.createdAt.toInstant(),
            paymentOrderStatus = PaymentOrderStatus.PENDING
        )
    }

    fun orderCancelledEventToOrderResponse(orderCancelledEvent: OrderCancelledEvent): PaymentRequestAvroModel {
        val order = orderCancelledEvent.order
        return PaymentRequestAvroModel(
            id = UUID.randomUUID(),
            orderId = order.id.value,
            sagaId = UUID.randomUUID(),
            customerId = order.customerId.value,
            price = order.price.amount,
            createdAt = orderCancelledEvent.createdAt.toInstant(),
            paymentOrderStatus = PaymentOrderStatus.CANCELLED
        )
    }

    fun orderPaidEventToRestaurantApprovalRequestAvroModel(domainEvent: OrderPaidEvent): RestaurantApprovalRequestAvroModel {
        val order = domainEvent.order
        return RestaurantApprovalRequestAvroModel(
            id = UUID.randomUUID(),
            sagaId = UUID.randomUUID(),
            orderId = order.id.value,
            restaurantId = order.restaurantId.value,
            restaurantOrderStatus = RestaurantOrderStatus.valueOf(order.orderStatus.toString()),
            products = order.items.map {
                Product(
                    id = it.product.productId.value.toString(),
                    quantity = it.quantity
                )
            },
            price = order.price.amount,
            createdAt = domainEvent.createdAt.toInstant()
        )
    }

    fun paymentResponseAvroModelToPaymentResponse(paymentResponseAvroModel: PaymentResponseAvroModel): PaymentResponse {
        return PaymentResponse(
            id = paymentResponseAvroModel.id.toString(),
            sagaId = paymentResponseAvroModel.sagaId.toString(),
            orderId = paymentResponseAvroModel.orderId.toString(),
            paymentId = paymentResponseAvroModel.paymentId.toString(),
            customerId = paymentResponseAvroModel.customerId.toString(),
            price = paymentResponseAvroModel.price,
            createdAt = paymentResponseAvroModel.createdAt,
            paymentStatus = PaymentStatus.valueOf(paymentResponseAvroModel.paymentStatus.toString()),
            failureMessages = paymentResponseAvroModel.failureMessages
        )
    }

    fun restaurantApprovalResponseAvroModelToRestaurantApprovalResponse(restaurantApprovalRequestAvroModel: RestaurantApprovalResponseAvroModel): RestaurantApprovalResponse {
        return RestaurantApprovalResponse(
            id = restaurantApprovalRequestAvroModel.id.toString(),
            sagaId = restaurantApprovalRequestAvroModel.sagaId.toString(),
            restaurantId = restaurantApprovalRequestAvroModel.restaurantId.toString(),
            orderId = restaurantApprovalRequestAvroModel.orderId.toString(),
            createdAt = restaurantApprovalRequestAvroModel.createdAt,
            orderApprovalStatus = OrderApprovalStatus.valueOf(restaurantApprovalRequestAvroModel.orderApprovalStatus.toString()),
            failureMessages = restaurantApprovalRequestAvroModel.failureMessages
        )
    }
}