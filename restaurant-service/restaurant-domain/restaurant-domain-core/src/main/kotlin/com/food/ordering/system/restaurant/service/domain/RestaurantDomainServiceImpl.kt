package com.food.ordering.system.restaurant.service.domain

import com.food.ordering.system.domain.DomainConstant.Companion.UTC
import com.food.ordering.system.domain.event.publisher.DomainEventPublisher
import com.food.ordering.system.restaurant.service.domain.entity.Restaurant
import com.food.ordering.system.restaurant.service.domain.event.OrderApprovalEvent
import com.food.ordering.system.restaurant.service.domain.event.OrderApprovedEvent
import com.food.ordering.system.restaurant.service.domain.event.OrderRejectedEvent
import org.slf4j.LoggerFactory
import java.time.ZoneId
import java.time.ZonedDateTime

class RestaurantDomainServiceImpl: RestaurantDomainService {
    private val logger = LoggerFactory.getLogger(RestaurantDomainServiceImpl::class.java)

    override fun validateOrder(
        restaurant: Restaurant,
        failureMessages: MutableList<String>,
        orderApprovedDomainEventPublisher: DomainEventPublisher<OrderApprovedEvent>,
        orderRejectedDomainEventPublisher: DomainEventPublisher<OrderRejectedEvent>
    ): OrderApprovalEvent {
        restaurant.validateOrder(failureMessages)

        return if (failureMessages.isEmpty()) {
            logger.info("Order is approved for restaurant with id: ${restaurant.id.value}")
            OrderApprovedEvent(
                restaurant.orderApproval,
                restaurant.id,
                failureMessages,
                ZonedDateTime.now(ZoneId.of(UTC)),
                orderApprovedDomainEventPublisher
            )
        } else {
            logger.info("Order is rejected for restaurant with id: ${restaurant.id.value}")
            OrderRejectedEvent(
                restaurant.orderApproval,
                restaurant.id,
                failureMessages,
                ZonedDateTime.now(ZoneId.of(UTC)),
                orderRejectedDomainEventPublisher
            )
        }
    }

}