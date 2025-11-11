package com.food.ordering.system.order.service.domain

import com.food.ordering.system.domain.event.EmptyEvent
import com.food.ordering.system.order.service.domain.dto.message.PaymentResponse
import com.food.ordering.system.order.service.domain.entity.Order
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent
import com.food.ordering.system.order.service.domain.exception.OrderNotFoundException
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.restaurantapproval.OrderPaidRestaurantRequestMessagePublisher
import com.food.ordering.system.saga.SagaStep
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

// This class represents a step in the saga for processing order payments.
// It implements the SagaStep interface with PaymentResponse as the input type,
// OrderPaidEvent as the success event type, and EmptyEvent as the rollback event type.
@Component
class OrderPaymentSaga(
    private val orderDomainService: OrderDomainService,
    private val orderSagaHelper: OrderSagaHelper,
    private val orderPaidRestaurantRequestMessagePublisher: OrderPaidRestaurantRequestMessagePublisher,
) : SagaStep<PaymentResponse, OrderPaidEvent, EmptyEvent> {
    private val logger = LoggerFactory.getLogger(OrderPaymentSaga::class.java)

    @Transactional
    override fun process(command: PaymentResponse): OrderPaidEvent {
        logger.info("Processing OrderPaymentSaga Event with order id: ${command.orderId}")
        val order = orderSagaHelper.findOrder(command.orderId)
        val orderPaidEvent = orderDomainService.payOrder(order, orderPaidRestaurantRequestMessagePublisher)
        orderSagaHelper.saveOrder(order)
        logger.info("Order with id ${command.orderId} is paid successfully")
        return orderPaidEvent
    }

    @Transactional
    override fun rollback(command: PaymentResponse): EmptyEvent {
        logger.info("Cancelling OrderPaymentSaga Event with order id: ${command.orderId}")
        val order = orderSagaHelper.findOrder(command.orderId)
        orderDomainService.cancelOrder(order, command.failureMessages ?: emptyList())
        orderSagaHelper.saveOrder(order)
        return EmptyEvent.INSTANCE
    }
}