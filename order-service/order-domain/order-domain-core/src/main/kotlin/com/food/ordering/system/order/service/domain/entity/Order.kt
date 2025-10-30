package com.food.ordering.system.order.service.domain.entity

import com.food.ordering.commondomain.entity.AggregateRoot
import com.food.ordering.commondomain.valueobject.*
import com.food.ordering.system.order.service.domain.exception.OrderDomainException
import com.food.ordering.system.order.service.domain.valueobject.OrderItemId
import com.food.ordering.system.order.service.domain.valueobject.TrackingId
import com.food.ordering.system.order.service.domain.valueobject.StreetAddress
import java.util.*

class Order(
    val customerId: CustomerId,
    val restaurantId: RestaurantId,
    val deliveryAddress: com.food.ordering.system.order.service.domain.valueobject.StreetAddress,
    val price: Money,
    val items: List<com.food.ordering.system.order.service.domain.entity.OrderItem>,

    var trackingId: com.food.ordering.system.order.service.domain.valueobject.TrackingId? = null,
    var orderStatus: OrderStatus = OrderStatus.PENDING,
    var failureMessages: List<String>? = null
): AggregateRoot<OrderId>() {
    companion object {
        val FAILURE_MESSAGE_DELIMITER = ","
    }

    fun initializeOrder() {
        this.id = OrderId(UUID.randomUUID())
        this.trackingId = com.food.ordering.system.order.service.domain.valueobject.TrackingId(UUID.randomUUID())
        initializeOrderItems(this.id)
    }

    private fun initializeOrderItems(orderId: OrderId) {
        var itemId = 1L
        this.items.forEach { orderItem ->
            orderItem.initializeOrderItem(orderId,
                com.food.ordering.system.order.service.domain.valueobject.OrderItemId(itemId)
            )
            itemId++
        }
    }

    fun validateOrder() {
        validateInitialOrder()
        validateTotalPrice()
        validateItemsPrice()
    }

    private fun validateInitialOrder() {

    }

    private fun validateTotalPrice() {
        if (!this.price.isGreaterThanZero()) {
            throw com.food.ordering.system.order.service.domain.exception.OrderDomainException("Total price must be greater than zero.")
        }
    }

    private fun validateItemsPrice() {
        val orderTotal = items.map { item ->
            validateItemPrice(item)
            item.subTotal
        }.fold(Money.ZERO, Money::plus)

        if (price != orderTotal) {
            throw com.food.ordering.system.order.service.domain.exception.OrderDomainException("Total price: ${price.amount} does not match sum of item prices: ${orderTotal.amount}")
        }
    }

    private fun validateItemPrice(item: com.food.ordering.system.order.service.domain.entity.OrderItem) {
        if (!item.isPriceValid()) {
            throw com.food.ordering.system.order.service.domain.exception.OrderDomainException("Order item price: ${item.price.amount} is not valid for product: ${item.product.productId.value}")
        }
    }

    fun pay() {
        if (orderStatus != OrderStatus.PENDING) {
            throw com.food.ordering.system.order.service.domain.exception.OrderDomainException("Order is not in correct state for pay operation.")
        }
        orderStatus = OrderStatus.PAID
    }

    fun approve() {
        if (orderStatus != OrderStatus.PAID) {
            throw com.food.ordering.system.order.service.domain.exception.OrderDomainException("Order is not in correct state for approve operation.")
        }
        orderStatus = OrderStatus.APPROVED
    }

    fun initCancel(failureMessages: List<String>) {
        if (orderStatus != OrderStatus.PAID) {
            throw com.food.ordering.system.order.service.domain.exception.OrderDomainException("Order is not in correct state for initCancel operation.")
        }
        orderStatus = OrderStatus.CANCELLING
        updateFailureMessages(failureMessages)
    }

    fun cancel(failureMessages: List<String>) {
        if (!(orderStatus == OrderStatus.PENDING || orderStatus == OrderStatus.CANCELLING)) {
            throw com.food.ordering.system.order.service.domain.exception.OrderDomainException("Order is not in correct state for cancel operation.")
        }
        orderStatus = OrderStatus.CANCELLING
        updateFailureMessages(failureMessages)
    }

    private fun updateFailureMessages(failureMessages: List<String>) {
        val messages = this.failureMessages?.toMutableList() ?: mutableListOf()
        messages.addAll(failureMessages.filter { it.isNotBlank() })
        this.failureMessages = messages
    }
}