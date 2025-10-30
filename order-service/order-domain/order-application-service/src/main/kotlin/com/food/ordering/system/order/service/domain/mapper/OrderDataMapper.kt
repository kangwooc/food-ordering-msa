package com.food.ordering.system.order.service.domain.mapper

import com.food.ordering.commondomain.valueobject.*
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse
import com.food.ordering.system.order.service.domain.entity.Order
import com.food.ordering.system.order.service.domain.entity.OrderItem
import com.food.ordering.system.order.service.domain.entity.Product
import com.food.ordering.system.order.service.domain.entity.Restaurant
import org.springframework.stereotype.Component
import java.util.*

@Component
class OrderDataMapper {
    fun createOrderCommandToRestaurant(command: CreateOrderCommand): Restaurant {
        val restaurant = Restaurant(
            products = command.items.map { item ->
                val product = Product(
                    name = "",
                    price = Money(item.price),
                    productId = ProductId(item.productId)
                )
                product.id = ProductId(item.productId)
                product
            },
            active = true
        )

        restaurant.id = RestaurantId(command.restaurantId)
        return restaurant
    }

    fun createOrderCommandToOrder(command: CreateOrderCommand): Order {
        val orderId = OrderId(UUID.randomUUID())
        val order = Order(
            customerId = CustomerId(command.customerId),
            restaurantId = RestaurantId(command.restaurantId),
            deliveryAddress = com.food.ordering.system.order.service.domain.valueobject.StreetAddress(
                id = UUID.randomUUID(),
                street = command.address.street,
                city = command.address.city,
                postalCode = command.address.postalCode,
            ),
            price = Money(command.price),
            items = createOrderItemsToOrderItemEntities(orderId, command.items),
        )
        order.id = orderId
        return order
    }

    private fun createOrderItemsToOrderItemEntities(orderId: OrderId, orderItem: List<com.food.ordering.system.order.service.domain.dto.create.OrderItem>): List<OrderItem> {
        return orderItem.map { item ->
            val orderItemEntity = OrderItem(
                product = Product(
                    price = Money(item.price),
                    productId = ProductId(item.productId)
                ),
                quantity = item.quantity,
                price = Money(item.price),
                subTotal = Money(item.subTotal),
                orderId = orderId
            )
            orderItemEntity.product.id = ProductId(item.productId)
            orderItemEntity
        }
    }

    fun createOrderToCreateOrderResponse(saveOrder: Order, message: String): CreateOrderResponse {
        return CreateOrderResponse(
            orderTrackingId = saveOrder.trackingId!!.value,
            orderStatus = saveOrder.orderStatus,
            message = message
        )
    }

    fun orderToTrackOrderResponse(order: Order): TrackOrderResponse {
        return TrackOrderResponse(
            orderTrackingId = order.trackingId!!.value,
            orderStatus = order.orderStatus,
            failureMessages = order.failureMessages
        )
    }
}