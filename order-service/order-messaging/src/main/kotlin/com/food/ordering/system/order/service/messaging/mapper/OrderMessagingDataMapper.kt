package com.food.ordering.system.order.service.messaging.mapper

import com.food.ordering.orderapplicationservice.dto.message.PaymentResponse
import com.food.ordering.orderapplicationservice.dto.message.RestaurantApprovalResponse
import com.food.ordering.orderdomaincore.event.OrderCancelledEvent
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent
import com.food.ordering.system.kafka.order.avro.model.*
import org.springframework.stereotype.Component
import java.util.*

@Component
class OrderMessagingDataMapper {
    fun orderCreatedEventToOrderResponse(orderCreatedEvent: com.food.ordering.system.order.service.domain.event.OrderCreatedEvent): PaymentRequestAvroModel {
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

    fun orderPaidEventToRestaurantApprovalRequestAvroModel(domainEvent: com.food.ordering.system.order.service.domain.event.OrderPaidEvent): RestaurantApprovalRequestAvroModel {
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
            paymentStatus = com.food.ordering.commondomain.valueobject.PaymentStatus.valueOf(paymentResponseAvroModel.paymentStatus.toString()),
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
            orderApprovalStatus = com.food.ordering.commondomain.valueobject.OrderApprovalStatus.valueOf(restaurantApprovalRequestAvroModel.orderApprovalStatus.toString()),
            failureMessages = restaurantApprovalRequestAvroModel.failureMessages
        )
    }
}