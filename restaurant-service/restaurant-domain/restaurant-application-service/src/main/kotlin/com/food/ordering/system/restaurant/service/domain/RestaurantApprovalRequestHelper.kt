package com.food.ordering.system.restaurant.service.domain

import com.food.ordering.system.domain.valueobject.OrderId
import com.food.ordering.system.restaurant.service.domain.dto.RestaurantApprovalRequest
import com.food.ordering.system.restaurant.service.domain.entity.Restaurant
import com.food.ordering.system.restaurant.service.domain.event.OrderApprovalEvent
import com.food.ordering.system.restaurant.service.domain.mapper.RestaurantDataMapper
import com.food.ordering.system.restaurant.service.domain.ports.output.message.publisher.OrderApprovedMessagePublisher
import com.food.ordering.system.restaurant.service.domain.ports.output.message.publisher.OrderRejectedMessagePublisher
import com.food.ordering.system.restaurant.service.domain.ports.output.repository.OrderApprovalRepository
import com.food.ordering.system.restaurant.service.domain.ports.output.repository.RestaurantRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
class RestaurantApprovalRequestHelper(
    private val restaurantDomainService: RestaurantDomainService,
    private val restaurantDataMapper: RestaurantDataMapper,
    private val restaurantRepository: RestaurantRepository,
    private val orderApprovalRepository: OrderApprovalRepository,
    private val orderApprovedMessagePublisher: OrderApprovedMessagePublisher,
    private val orderRejectedMessagePublisher: OrderRejectedMessagePublisher
) {
    private val logger = LoggerFactory.getLogger(RestaurantApprovalRequestHelper::class.java)

    @Transactional
    fun persistOrderApproval(restaurantApprovalRequest: RestaurantApprovalRequest): OrderApprovalEvent {
        logger.info("Persisting order approval for order ${restaurantApprovalRequest.orderId}")

        val failureMessages = mutableListOf<String>()
        val restaurant = findRestaurant(restaurantApprovalRequest)
        val orderApprovalEvent = restaurantDomainService.validateOrder(
            restaurant,
            failureMessages,
            orderApprovedMessagePublisher,
            orderRejectedMessagePublisher
        )
        return orderApprovalRepository.save(restaurant.orderApproval)
            .also {
                logger.info("Order approval is saved for order ${restaurantApprovalRequest.orderId}")
            }
            .let { orderApprovalEvent }
    }

    private fun findRestaurant(restaurantApprovalRequest: RestaurantApprovalRequest): Restaurant {
        val restaurant = restaurantDataMapper
            .restaurantApprovalRequestToRestaurant(restaurantApprovalRequest)
        val result = restaurantRepository.findRestaurantInformation(restaurant)
        if (result == null) {
            logger.warn("Restaurant with id ${restaurant.id.value} not found!")
            throw IllegalArgumentException("Restaurant with id ${restaurant.id.value} not found!")
        }

        restaurant.active = result.active
        restaurant.orderDetail.products.forEach { product ->
            result.orderDetail.products.forEach { p ->
                if (product.id == p.id) {
                    p.updateWithConfirmedNamePriceAndAvailability(
                        name = p.name,
                        price = p.price,
                        available = p.available
                    )
                }
            }
        }

        restaurant.orderDetail.id = OrderId(UUID.fromString(restaurantApprovalRequest.orderId))
        return restaurant
    }
}