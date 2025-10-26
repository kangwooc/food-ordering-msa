package com.food.ordering.orderdomaincore

import com.food.ordering.orderdomaincore.entity.Order
import com.food.ordering.orderdomaincore.entity.Restaurant
import com.food.ordering.orderdomaincore.event.OrderCancelledEvent
import com.food.ordering.orderdomaincore.event.OrderCreatedEvent
import com.food.ordering.orderdomaincore.event.OrderPaidEvent
import com.food.ordering.orderdomaincore.exception.OrderDomainException
import org.slf4j.LoggerFactory
import java.time.ZoneId
import java.time.ZonedDateTime

class OrderDomainServiceImpl: OrderDomainService {
    private val logger = LoggerFactory.getLogger(OrderDomainServiceImpl::class.java)
    private val UTC = "UTC"

    override fun validateAndInitiateOrder(order: Order, restaurant: Restaurant): OrderCreatedEvent {
        validateRestaurant(restaurant)
        setOrderProductInformation(order, restaurant)
        order.validateOrder()
        order.initializeOrder()

        logger.info("Order ${order.id.value} created")

        return OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of(UTC)))
    }

    override fun payOrder(order: Order): OrderPaidEvent {
        order.pay()
        logger.info("Order ${order.id.value} paid")

        return OrderPaidEvent(order, ZonedDateTime.now(ZoneId.of(UTC)))
    }

    override fun approveOrder(order: Order) {
        order.approve()
        logger.info("Order ${order.id.value} approved")
    }

    override fun cancelOrderPayment(order: Order, failureMessages: List<String>): OrderCancelledEvent {
        order.initCancel(failureMessages)
        logger.info("Order ${order.id.value} cancelling payment")

        return OrderCancelledEvent(order, ZonedDateTime.now(ZoneId.of(UTC)))
    }

    override fun cancelOrder(order: Order, failureMessages: List<String>) {
        order.cancel(failureMessages)
        logger.info("Order ${order.id.value} cancelled")
    }

    private fun validateRestaurant(restaurant: Restaurant) {
        if (!restaurant.isActive()) {
            throw OrderDomainException("Restaurant with id ${restaurant.id.value} is not active")
        }
    }

    private fun setOrderProductInformation(order: Order, restaurant: Restaurant) {
        val productById = restaurant.products.associateBy { it.id }
        order.items.forEach { orderItem ->
            val currentProduct = productById[orderItem.product.id]
                ?: throw OrderDomainException("Product with id ${orderItem.product.id.value} not found in restaurant ${restaurant.id.value}")
            currentProduct.updateWithConfirmedNameAndPrice(
                orderItem.product.name,
                orderItem.product.price
            )
        }
    }
}
