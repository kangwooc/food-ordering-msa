package com.food.ordering.system.order.service.domain

import com.food.ordering.system.domain.event.EmptyEvent
import com.food.ordering.system.order.service.domain.dto.message.RestaurantApprovalResponse
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCancelledPaymentRequestMessagePublisher
import com.food.ordering.system.saga.SagaStep
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class OrderApprovalSaga(
    private val orderDomainService: OrderDomainService,
    private val orderSagaHelper: OrderSagaHelper,
    private val orderCancelledPaymentRequestMessagePublisher: OrderCancelledPaymentRequestMessagePublisher,
) : SagaStep<RestaurantApprovalResponse, EmptyEvent, OrderCancelledEvent> {
    private val logger = LoggerFactory.getLogger(OrderApprovalSaga::class.java)

    @Transactional
    override fun process(command: RestaurantApprovalResponse): EmptyEvent {
        logger.info("Approving order Event with order id: ${command.orderId}")
        val order = orderSagaHelper.findOrder(command.orderId)
        orderDomainService.approveOrder(order)
        orderSagaHelper.saveOrder(order)
        logger.info("Order with id ${command.orderId} is approved successfully")
        return EmptyEvent.INSTANCE
    }

    @Transactional
    override fun rollback(command: RestaurantApprovalResponse): OrderCancelledEvent {
        logger.info("Cancelling order Event with order id: ${command.orderId}")
        val order = orderSagaHelper.findOrder(command.orderId)
        val orderCancelledEvent = orderDomainService.cancelOrderPayment(
            order,
            command.failureMessages ?: emptyList(),
            orderCancelledPaymentRequestMessagePublisher
        )
        orderSagaHelper.saveOrder(order)
        logger.info("Order with id ${command.orderId} is cancelled successfully")
        return orderCancelledEvent
    }
}