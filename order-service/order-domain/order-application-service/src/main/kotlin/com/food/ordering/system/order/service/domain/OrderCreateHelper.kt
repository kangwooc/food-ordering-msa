package com.food.ordering.orderapplicationservice

import com.food.ordering.orderapplicationservice.dto.create.CreateOrderCommand
import com.food.ordering.orderapplicationservice.mapper.OrderDataMapper
import com.food.ordering.orderapplicationservice.ports.output.repository.CustomerRepository
import com.food.ordering.orderapplicationservice.ports.output.repository.OrderRepository
import com.food.ordering.orderapplicationservice.ports.output.repository.RestaurantRepository
import com.food.ordering.system.order.service.domain.OrderDomainService
import com.food.ordering.orderdomaincore.entity.Order
import com.food.ordering.orderdomaincore.entity.Restaurant
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent
import com.food.ordering.system.order.service.domain.exception.OrderDomainException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
class OrderCreateHelper(
    private val orderDomainService: com.food.ordering.system.order.service.domain.OrderDomainService,
    private val orderRepository: OrderRepository,
    private val customerRepository: CustomerRepository,
    private val restaurantRepository: RestaurantRepository,
    private val orderDataMapper: OrderDataMapper,
) {
    private val logger = LoggerFactory.getLogger(OrderCreateHelper::class.java)

    @Transactional
    fun persistOrder(command: CreateOrderCommand): com.food.ordering.system.order.service.domain.event.OrderCreatedEvent {
        checkCustomer(command.customerId)
        val restaurant = checkRestaurant(command)
        val order = orderDataMapper.createOrderCommandToOrder(command)
        val orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, restaurant)
        saveOrder(orderCreatedEvent)
        logger.info("saving order ${order.id}")
        return orderCreatedEvent
    }

    private fun checkCustomer(customerId: UUID) {
        val customer = customerRepository.findCustomer(customerId)
        if (customer == null) {
            logger.warn("Customer with id $customerId not found")
            throw com.food.ordering.system.order.service.domain.exception.OrderDomainException("Customer with id $customerId not found")
        }
    }

    private fun checkRestaurant(command: CreateOrderCommand): Restaurant {
        val restaurant = orderDataMapper.createOrderCommandToRestaurant(command)
        val optionalRestaurant = restaurantRepository.findRestaurantInformation(restaurant)
        if (optionalRestaurant == null) {
            logger.warn("Restaurant with id ${restaurant.id.value} not found")
            throw com.food.ordering.system.order.service.domain.exception.OrderDomainException("Restaurant with id ${restaurant.id.value} not found")
        }

        return optionalRestaurant
    }

    private fun saveOrder(orderCreatedEvent: com.food.ordering.system.order.service.domain.event.OrderCreatedEvent): Order {
        val order = orderCreatedEvent.order
        val savedOrder = orderRepository.save(order) ?: throw com.food.ordering.system.order.service.domain.exception.OrderDomainException(
            "Could not save order"
        )
        logger.info("Order with id ${savedOrder.id.value} is saved successfully")
        return savedOrder
    }
}