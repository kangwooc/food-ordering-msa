package com.food.ordering.orderdomaincore.entity

import com.food.ordering.commondomain.entity.AggregateRoot
import com.food.ordering.commondomain.valueobject.*
import com.food.ordering.orderdomaincore.exception.OrderDomainException
import com.food.ordering.orderdomaincore.valueobject.OrderItemId
import com.food.ordering.orderdomaincore.valueobject.TrackingId
import com.food.ordering.orderdomaincore.valueobject.StreetAddress
import java.util.*

class Order(
    val customerId: CustomerId,
    val restaurantId: RestaurantId,
    val deliveryAddress: StreetAddress,
    val price: Money,
    val items: List<OrderItem>,

    var trackingId: TrackingId? = null,
    var orderStatus: OrderStatus? = null,
    var failureMessages: List<String>? = null
): AggregateRoot<OrderId>() {
    fun initializeOrder() {
        this.id = OrderId(UUID.randomUUID())
        this.trackingId = TrackingId(UUID.randomUUID())
        initializeOrderItems(this.id)
    }

    private fun initializeOrderItems(orderId: OrderId) {
        var itemId = 1L
        this.items.forEach { orderItem ->
            orderItem.initializeOrderItem(orderId, OrderItemId(itemId))
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
            throw OrderDomainException("Total price must be greater than zero.")
        }
    }

    private fun validateItemsPrice() {
        val orderTotal = items.map { item ->
            validateItemPrice(item)
            item.subTotal
        }.fold(Money.ZERO, Money::plus)

        if(price != orderTotal) {
            throw OrderDomainException("Total price: $price does not match sum of item prices: $orderTotal")
        }
    }

    private fun validateItemPrice(item: OrderItem) {
        if (!item.isPriceValid()) {
            throw OrderDomainException("Order item price: ${item.price} is not valid for product: ${item.product.productId.value}")
        }
    }

    fun pay() {
        if (orderStatus != OrderStatus.PENDING) {
            throw OrderDomainException("Order is not in correct state for pay operation.")
        }
        orderStatus = OrderStatus.PAID
    }

    fun approve() {
        if (orderStatus != OrderStatus.PAID) {
            throw OrderDomainException("Order is not in correct state for approve operation.")
        }
        orderStatus = OrderStatus.APPROVED
    }

    fun initCancel(failureMessages: List<String>) {
        if (orderStatus != OrderStatus.PAID) {
            throw OrderDomainException("Order is not in correct state for initCancel operation.")
        }
        orderStatus = OrderStatus.CANCELLING
        updateFailureMessages(failureMessages)
    }

    fun cancel(failureMessages: List<String>) {
        if (!(orderStatus == OrderStatus.PENDING || orderStatus == OrderStatus.CANCELLING)) {
            throw OrderDomainException("Order is not in correct state for cancel operation.")
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