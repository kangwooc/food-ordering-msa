package com.food.ordering.system.order.service.dataaccess.order.adapter

import com.food.ordering.orderapplicationservice.ports.output.repository.OrderRepository
import com.food.ordering.orderdomaincore.entity.Order
import com.food.ordering.system.order.service.domain.valueobject.TrackingId
import com.food.ordering.system.order.service.dataaccess.order.mapper.OrderDataAccessMapper
import com.food.ordering.system.order.service.dataaccess.order.repository.OrderJpaRepository
import org.springframework.stereotype.Component

@Component
class OrderRepositoryImpl(
    private val orderJpaRepository: OrderJpaRepository,
    private val orderDataAccessMapper: OrderDataAccessMapper,
): OrderRepository {
    override fun save(order: Order): Order? {
        return orderDataAccessMapper.orderEntityToOrder(
            orderJpaRepository.save(
                orderDataAccessMapper.orderToOrderEntity(order)
            )
        )
    }

    override fun findByTrackingId(trackingId: com.food.ordering.system.order.service.domain.valueobject.TrackingId): Order? {
        return orderJpaRepository.findByTrackingId(trackingId.value)?.let {
            orderDataAccessMapper.orderEntityToOrder(it)
        }
    }
}