package com.food.ordering.orderapplicationservice

import com.food.ordering.orderapplicationservice.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher
import com.food.ordering.orderdomaincore.event.OrderCreatedEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
class OrderCreatedEventApplicationListener(
     private val orderCreatedPaymentRequestMessagePublisher: OrderCreatedPaymentRequestMessagePublisher
) {
    // @TransactionalEventListener는 특정 이벤트가 발생한 후에
    // 그 이벤트에 대한 처리를 원하는 트랜잭션 내부에서 수행할 수 있도록 하는 리스너입니다.
    // 이 어노테이션을 사용하면 이벤트가 발생하고 관련 트랜잭션이 커밋된 후에만 이벤트 처리 메서드가 호출됩니다.
    //
    // 다음은 이 어노테이션의 주요 용도입니다:
    //
    // 트랜잭션 안전성: 이벤트가 처리되는 시점을 트랜잭션 커밋 이후로 보장하여, 데이터의 일관성을 유지할 수 있습니다.
    // 이를 통해 이벤트 처리 중 오류가 발생할 경우 데이터가 손상되지 않도록 합니다.
    //
    // 비동기 처리: 이벤트가 생성된 후 응답성을 높이기 위해 비동기로 처리되도록 설계할 수 있습니다.
    // 이는 시스템의 전체적인 성능을 향상시키는 데 기여합니다.
    //
    // 상관된 작업의 그룹화: 이벤트 발생 이후 그것과 관련된 여러 작업을 함께 처리할 수 있어, 코드 구조가 보다 명확해집니다.
    //
    // 예를 들어, Order 생성 후에 관련된 비즈니스 로직을 수행하는 데 사용할 수 있으며,
    // 이 경우 OrderCreateCommandHandler에서 트랜잭션이 완료된 후 이벤트를 발행하여,
    // TransactionalEventListener가 이를 처리하도록 할 수 있습니다.
    @TransactionalEventListener
    fun process(event: OrderCreatedEvent) {
        orderCreatedPaymentRequestMessagePublisher.publish(event)
    }
}