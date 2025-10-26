package com.food.ordering.orderapplicationservice

import com.food.ordering.orderapplicationservice.dto.create.CreateOrderCommand
import com.food.ordering.orderapplicationservice.dto.create.CreateOrderResponse
import com.food.ordering.orderapplicationservice.mapper.OrderDataMapper
import com.food.ordering.orderapplicationservice.ports.output.repository.CustomerRepository
import com.food.ordering.orderapplicationservice.ports.output.repository.OrderRepository
import com.food.ordering.orderapplicationservice.ports.output.repository.RestaurantRepository
import com.food.ordering.orderdomaincore.OrderDomainService
import com.food.ordering.orderdomaincore.entity.Order
import com.food.ordering.orderdomaincore.entity.Restaurant
import com.food.ordering.orderdomaincore.event.OrderCreatedEvent
import com.food.ordering.orderdomaincore.exception.OrderDomainException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
class OrderCreateCommandHandler(
    private val orderDomainService: OrderDomainService,
    private val orderRepository: OrderRepository,
    private val customerRepository: CustomerRepository,
    private val restaurantRepository: RestaurantRepository,
    private val orderDataMapper: OrderDataMapper
) {
    private val logger = LoggerFactory.getLogger(OrderCreateCommandHandler::class.java)

    @Transactional
    fun createOrder(command: CreateOrderCommand): CreateOrderResponse {
        checkCustomer(command.customerId)
        val restaurant = checkRestaurant(command)
        val order = orderDataMapper.createOrderCommandToOrder(command)
        val orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, restaurant)
        val saveOrder = saveOrder(orderCreatedEvent)
        logger.info("saving order ${order.id}")
        return orderDataMapper.createOrderToCreateOrderResponse(saveOrder)
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

        return optionalRestaurant
    }

    private fun saveOrder(orderCreatedEvent: OrderCreatedEvent): Order {
        val order = orderCreatedEvent.order
        val savedOrder = orderRepository.save(order) ?: throw OrderDomainException("Could not save order")
        logger.info("Order with id ${savedOrder.id.value} is saved successfully")
        return savedOrder
    }
}