package com.food.ordering.system.order.service.domain

import com.food.ordering.system.domain.valueobject.OrderId
import com.food.ordering.system.order.service.domain.entity.Order
import com.food.ordering.system.order.service.domain.exception.OrderNotFoundException
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*

@Component
class OrderSagaHelper(
    private val orderRepository: OrderRepository,
) {
    private val logger = LoggerFactory.getLogger(OrderSagaHelper::class.java)

    fun findOrder(orderId: String): Order {
        val order = orderRepository.findById(OrderId(UUID.fromString(orderId)))
        if (order == null) {
            logger.error("Order with id $orderId not found")
            throw OrderNotFoundException("Order with id $orderId not found")
        }
        return order
    }

    fun saveOrder(order: Order): Order? {
        return orderRepository.save(order)
    }
}