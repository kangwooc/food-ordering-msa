package com.food.ordering.system.order.service.domain

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class OrderCreateCommandHandler(
    private val orderCreateHelper: OrderCreateHelper,
    private val orderDataMapper: OrderDataMapper,
    private val orderCreatedPaymentRequestMessagePublisher: OrderCreatedPaymentRequestMessagePublisher,
) {
    private val logger = LoggerFactory.getLogger(OrderCreateCommandHandler::class.java)

    // 트랜잭션 어노테이션(@Transactional)을 사용하는 메서드는 public이어야 합니다.
    // 이는 Spring AOP(관점 지향 프로그래밍) 원리에 의해,
    // 트랜잭션 어노테이션이 적용된 메서드는 반드시 다른 빈에서 호출되어야 하고,
    // 이러한 메서드가 public 접근 수식자를 가져야만 Spring이 해당 메서드에 대한 프록시를 생성할 수 있기 때문입니다.
    //
    // 트랜잭션 어노테이션을 사용하려면 해당 메서드가 public이어야 함.
    // 트랜잭션 어노테이션이 있는 메서드는 다른 빈에서 호출되어야 함.
    // 내부 메서드에서 호출될 경우, @Transactional이 적용되지 않음.
    // 따라서, 트랜잭션을 제대로 활용하고 싶다면 메서드를 public으로 선언하는 것이 중요합니다.
    // 이 방식이 Spring에서 트랜잭션 관리 기능을 제대로 작동하게 만듭니다.
    @Transactional
    fun createOrder(command: CreateOrderCommand): CreateOrderResponse {
        val orderCreatedEvent = orderCreateHelper.persistOrder(command)
        logger.info("Order created with id ${orderCreatedEvent.order.id.value}")
        orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent)
        return orderDataMapper.createOrderToCreateOrderResponse(orderCreatedEvent.order, "Order created successfully")
    }
}