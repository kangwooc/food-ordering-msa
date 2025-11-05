package com.food.ordering.system.order.service.domain

import com.food.ordering.system.domain.DomainConstant.Companion.UTC
import com.food.ordering.system.domain.event.publisher.DomainEventPublisher
import com.food.ordering.system.order.service.domain.entity.Order
import com.food.ordering.system.order.service.domain.entity.Restaurant
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent
import com.food.ordering.system.order.service.domain.exception.OrderDomainException
import org.slf4j.LoggerFactory
import java.time.ZoneId
import java.time.ZonedDateTime

class OrderDomainServiceImpl : OrderDomainService {
    private val logger = LoggerFactory.getLogger(OrderDomainServiceImpl::class.java)

    override fun validateAndInitiateOrder(
        order: Order,
        restaurant: Restaurant,
        orderCreatedEventPublisher: DomainEventPublisher<OrderCreatedEvent>
    ): OrderCreatedEvent {
        validateRestaurant(restaurant)
        setOrderProductInformation(order, restaurant)
        order.validateOrder()
        order.initializeOrder()

        logger.info("Order ${order.id.value} created")

        return OrderCreatedEvent(
            order,
            ZonedDateTime.now(ZoneId.of(UTC)),
            orderCreatedEventPublisher
        )
    }

    override fun payOrder(order: Order, orderPaidEventPublisher: DomainEventPublisher<OrderPaidEvent>): OrderPaidEvent {
        order.pay()
        logger.info("Order ${order.id.value} paid")

        return OrderPaidEvent(
            order,
            ZonedDateTime.now(ZoneId.of(UTC)),
            orderPaidEventPublisher
        )
    }

    override fun approveOrder(order: Order) {
        order.approve()
        logger.info("Order ${order.id.value} approved")
    }

    override fun cancelOrderPayment(
        order: Order,
        failureMessages: List<String>,
        orderCancelledEventPublisher: DomainEventPublisher<OrderCancelledEvent>
    ): OrderCancelledEvent {
        order.initCancel(failureMessages)
        logger.info("Order ${order.id.value} cancelling payment")

        return OrderCancelledEvent(order, ZonedDateTime.now(ZoneId.of(UTC)), orderCancelledEventPublisher)
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
        val productById = restaurant.products.associateBy { it.productId }
        order.items.forEach { orderItem ->
            val currentProduct = productById[orderItem.product.id]
                ?: throw OrderDomainException("Product with id ${orderItem.product.id.value} not found in restaurant ${restaurant.id.value}")
            currentProduct.updateWithConfirmedNameAndPrice(
                orderItem.product.name ?: "",
                orderItem.product.price
            )
        }
    }
}
