package com.food.ordering.system.order.service.dataaccess.order.mapper

import com.food.ordering.commondomain.valueobject.*
import com.food.ordering.system.order.service.dataaccess.order.entity.OrderAddressEntity
import com.food.ordering.system.order.service.dataaccess.order.entity.OrderEntity
import com.food.ordering.system.order.service.dataaccess.order.entity.OrderItemEntity
import com.food.ordering.system.order.service.domain.entity.Order
import org.springframework.stereotype.Component

@Component
class OrderDataAccessMapper {
    fun orderToOrderEntity(order: Order): OrderEntity {
        val orderEntity = OrderEntity(
            id = order.id.value,
            customerId = order.customerId.value,
            restaurantId = order.restaurantId.value,
            trackingId = order.trackingId!!.value,
            price = order.price.amount,
            status = order.orderStatus,
            address = deliveryAddressToOrderAddressEntity(order.deliveryAddress),
            items = orderItemsToOrderItemEntities(order.items),
            failureMessages = order.failureMessages?.joinToString(Order.FAILURE_MESSAGE_DELIMITER) ?: "",
        )

        orderEntity.address.order = orderEntity
        orderEntity.items.forEach { it.order = orderEntity }
        return orderEntity
    }

    private fun deliveryAddressToOrderAddressEntity(deliveryAddress: com.food.ordering.system.order.service.domain.valueobject.StreetAddress): OrderAddressEntity {
        return OrderAddressEntity(
            id = deliveryAddress.id,
            street = deliveryAddress.street,
            postalCode = deliveryAddress.postalCode,
            city = deliveryAddress.city,
        )
    }

    private fun orderItemsToOrderItemEntities(items: List<com.food.ordering.system.order.service.domain.entity.OrderItem>): MutableList<OrderItemEntity> {
        val itemEntities = mutableListOf<OrderItemEntity>()
        items.forEach { item ->
            val itemEntity = OrderItemEntity(
                id = item.orderId.value,
                productId = item.product.productId.value,
                quantity = item.quantity,
                price = item.price.amount,
                subTotal = item.subTotal.amount,
            )
            itemEntities.add(itemEntity)
        }
        return itemEntities
    }

    fun orderEntityToOrder(orderEntity: OrderEntity): Order {
        val order =  Order(
            customerId = CustomerId(orderEntity.customerId),
            restaurantId = RestaurantId(orderEntity.restaurantId),
            deliveryAddress = orderAddressEntityToStreetAddress(orderEntity.address),
            price = Money(orderEntity.price),
            items = orderItemEntitiesToOrderItems(orderEntity.items),
            trackingId = com.food.ordering.system.order.service.domain.valueobject.TrackingId(orderEntity.trackingId),
            orderStatus = orderEntity.status,
            failureMessages = orderEntity.failureMessages?.split(Order.FAILURE_MESSAGE_DELIMITER)
        )
        order.id = OrderId(orderEntity.id)
        return order
    }

    private fun orderAddressEntityToStreetAddress(addressEntity: OrderAddressEntity): com.food.ordering.system.order.service.domain.valueobject.StreetAddress {
        return com.food.ordering.system.order.service.domain.valueobject.StreetAddress(
            id = addressEntity.id,
            street = addressEntity.street,
            postalCode = addressEntity.postalCode,
            city = addressEntity.city,
        )
    }

    private fun orderItemEntitiesToOrderItems(itemEntities: List<OrderItemEntity>): List<com.food.ordering.system.order.service.domain.entity.OrderItem> {
        val items = mutableListOf<com.food.ordering.system.order.service.domain.entity.OrderItem>()
        itemEntities.forEach { itemEntity ->
            val item = com.food.ordering.system.order.service.domain.entity.OrderItem(
                orderId = OrderId(itemEntity.id!!),
                product = com.food.ordering.system.order.service.domain.entity.Product(
                    productId = ProductId(itemEntity.productId),
                    price = Money(itemEntity.price)
                ),
                quantity = itemEntity.quantity,
                price = Money(itemEntity.price),
                subTotal = Money(itemEntity.subTotal)
            )
            items.add(item)
        }
        return items
    }
}