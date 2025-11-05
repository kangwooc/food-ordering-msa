package com.food.ordering.system.order.service.domain

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand
import com.food.ordering.system.order.service.domain.entity.Order
import com.food.ordering.system.order.service.domain.entity.Restaurant
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent
import com.food.ordering.system.order.service.domain.exception.OrderDomainException
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher
import com.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository
import com.food.ordering.system.order.service.domain.ports.output.repository.RestaurantRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
class OrderCreateHelper(
    private val orderDomainService: OrderDomainService,
    private val orderRepository: OrderRepository,
    private val customerRepository: CustomerRepository,
    private val restaurantRepository: RestaurantRepository,
    private val orderDataMapper: OrderDataMapper,
    private val orderCreatedPaymentRequestMessagePublisher: OrderCreatedPaymentRequestMessagePublisher
) {
    private val logger = LoggerFactory.getLogger(OrderCreateHelper::class.java)

    @Transactional
    fun persistOrder(command: CreateOrderCommand): OrderCreatedEvent {
        checkCustomer(command.customerId)
        val restaurant = checkRestaurant(command)
        val order = orderDataMapper.createOrderCommandToOrder(command)
        val orderCreatedEvent =
            orderDomainService.validateAndInitiateOrder(order, restaurant, orderCreatedPaymentRequestMessagePublisher)
        saveOrder(orderCreatedEvent)
        logger.info("saving order ${order.id}")
        return orderCreatedEvent
    }

    private fun checkCustomer(customerId: UUID) {
        val customer = customerRepository.findCustomer(customerId)
        if (customer == null) {
            logger.warn("Customer with id $customerId not found")
            throw OrderDomainException("Customer with id $customerId not found")
        }
    }

    private fun checkRestaurant(command: CreateOrderCommand): Restaurant {
        val restaurant = orderDataMapper.createOrderCommandToRestaurant(command)
        val optionalRestaurant = restaurantRepository.findRestaurantInformation(restaurant)
        if (optionalRestaurant == null) {
            logger.warn("Restaurant with id ${restaurant.id.value} not found")
            throw OrderDomainException("Restaurant with id ${restaurant.id.value} not found")
        }

        return restaurant
    }

    private fun saveOrder(orderCreatedEvent: OrderCreatedEvent): Order {
        val order = orderCreatedEvent.order
        val savedOrder = orderRepository.save(order) ?: throw OrderDomainException("Could not save order")
        logger.info("Order with id ${savedOrder.id.value} is saved successfully")
        return savedOrder
    }
}