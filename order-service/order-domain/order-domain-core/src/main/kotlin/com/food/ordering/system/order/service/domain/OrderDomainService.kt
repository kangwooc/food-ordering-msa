package com.food.ordering.orderdomaincore

import com.food.ordering.orderdomaincore.entity.Order
import com.food.ordering.orderdomaincore.entity.Restaurant
import com.food.ordering.orderdomaincore.event.OrderCancelledEvent
import com.food.ordering.orderdomaincore.event.OrderCreatedEvent
import com.food.ordering.orderdomaincore.event.OrderPaidEvent

// 도메인 서비스에서 도메인 이벤트를 반환
// 주문 검증, 결제 처리, 승인 및 취소 로직을 포함
// 이 부분은 어플리케이션 서비스에서 호출되어 비즈니스 로직을 수행
// 비즈니스 로직의 집중: 도메인 서비스는 주로 비즈니스 로직을 실행하는 곳입니다.
// 이벤트를 도메인 서비스에서 생성하고 반환함으로써, 핵심 비즈니스 로직과 이벤트 발생의 책임을 명확히 구분할 수 있습니다.
// 영속성 및 트랜잭션 관리: 도메인 이벤트는 어플리케이션 서비스에서 발행됩니다.
// 이 접근 방식은 엔티티나 도메인 서비스가 영속화된 후에 이벤트가 게시되도록 보장합니다.
// 이는 데이터베이스 트랜잭션이 완료된 후에 이벤트를 발행함으로써, 잘못된 이벤트가 발생하는 것을 방지합니다.
// 어플리케이션 논리와의 분리: 도메인 서비스가 이벤트를 반환하고, 어플리케이션 서비스에서 이를 수신하여 처리하는 구조는
// 도메인에서 어플리케이션의 세부 구현으로의 의존성을 줄여줍니다.
// 이렇게 하면 도메인 로직이 변경되더라도 어플리케이션 서비스는 영향을 받지 않게 됩니다.
interface OrderDomainService {
    fun validateAndInitiateOrder(order: Order, restaurant: Restaurant): OrderCreatedEvent

    fun payOrder(order: Order): OrderPaidEvent

    fun approveOrder(order: Order)

    fun cancelOrderPayment(order: Order, failureMessages: List<String>): OrderCancelledEvent

    fun cancelOrder(order: Order, failureMessages: List<String>)
}