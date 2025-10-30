package com.food.ordering.system.order.service.messaging.mapper

import com.food.ordering.orderdomaincore.event.OrderCancelledEvent
import com.food.ordering.orderdomaincore.event.OrderCreatedEvent
import com.food.ordering.orderdomaincore.event.OrderPaidEvent
import com.food.ordering.system.kafka.order.avro.model.*
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
}