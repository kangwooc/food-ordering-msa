package com.food.ordering.system.restaurant.service.messaging.mapper

import com.food.ordering.system.domain.valueobject.Money
import com.food.ordering.system.domain.valueobject.ProductId
import com.food.ordering.system.domain.valueobject.RestaurantOrderStatus
import com.food.ordering.system.kafka.order.avro.model.OrderApprovalStatus
import com.food.ordering.system.kafka.order.avro.model.RestaurantApprovalRequestAvroModel
import com.food.ordering.system.kafka.order.avro.model.RestaurantApprovalResponseAvroModel
import com.food.ordering.system.restaurant.service.domain.dto.RestaurantApprovalRequest
import com.food.ordering.system.restaurant.service.domain.entity.Product
import com.food.ordering.system.restaurant.service.domain.event.OrderApprovalEvent
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.util.*

@Component
class RestaurantMessagingDataMapper {
    fun orderApprovedEventToRestaurantApprovalResponseAvroModel(orderApprovalEvent: OrderApprovalEvent): RestaurantApprovalResponseAvroModel {
        return RestaurantApprovalResponseAvroModel(
            id = orderApprovalEvent.orderApproval.id.value,
            sagaId = UUID.fromString(""),
            orderId = orderApprovalEvent.orderApproval.orderId.value,
            restaurantId = orderApprovalEvent.orderApproval.restaurantId.value,
            orderApprovalStatus = OrderApprovalStatus.valueOf(orderApprovalEvent.orderApproval.orderApprovalStatus!!.name),
            failureMessages = orderApprovalEvent.failureMessages,
            createdAt = orderApprovalEvent.createdAt.toInstant()
        )
    }

    fun orderRejectedEventToRestaurantApprovalResponseAvroModel(orderApprovalEvent: OrderApprovalEvent): RestaurantApprovalResponseAvroModel {
        return RestaurantApprovalResponseAvroModel(
            id = orderApprovalEvent.orderApproval.id.value,
            sagaId = UUID.fromString(""),
            orderId = orderApprovalEvent.orderApproval.orderId.value,
            restaurantId = orderApprovalEvent.orderApproval.restaurantId.value,
            orderApprovalStatus = OrderApprovalStatus.valueOf(orderApprovalEvent.orderApproval.orderApprovalStatus!!.name),
            failureMessages = orderApprovalEvent.failureMessages,
            createdAt = orderApprovalEvent.createdAt.toInstant()
        )
    }

    fun restaurantApprovalRequestAvroModelToRestaurantApprovalRequest(
        restaurantApprovalRequestAvroModel: RestaurantApprovalRequestAvroModel
    ) : RestaurantApprovalRequest {
        return RestaurantApprovalRequest(
            orderId = restaurantApprovalRequestAvroModel.orderId.toString(),
            restaurantId = restaurantApprovalRequestAvroModel.restaurantId.toString(),
            restaurantOrderStatus = RestaurantOrderStatus.valueOf(
                restaurantApprovalRequestAvroModel.restaurantOrderStatus.name
            ),
            price = restaurantApprovalRequestAvroModel.price,
            products = restaurantApprovalRequestAvroModel.products.map { product ->
                Product(
                    quantity = product.quantity,
                    name = "",
                    price = Money(BigDecimal.ZERO),
                    available = true,
                ).apply { id = ProductId(UUID.fromString(product.id)) }
            },
            id = restaurantApprovalRequestAvroModel.id.toString(),
            sagaId = restaurantApprovalRequestAvroModel.sagaId.toString(),
            createdAt = restaurantApprovalRequestAvroModel.createdAt,
        )
    }
}