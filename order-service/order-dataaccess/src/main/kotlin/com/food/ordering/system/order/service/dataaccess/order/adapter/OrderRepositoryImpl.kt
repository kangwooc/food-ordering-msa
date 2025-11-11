package com.food.ordering.system.order.service.dataaccess.order.adapter

import com.food.ordering.system.domain.valueobject.OrderId
import com.food.ordering.system.order.service.dataaccess.order.mapper.OrderDataAccessMapper
import com.food.ordering.system.order.service.dataaccess.order.repository.OrderJpaRepository
import com.food.ordering.system.order.service.domain.entity.Order
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository
import org.springframework.data.repository.findByIdOrNull
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

    override fun findById(orderId: OrderId): Order? {
        val orderEntity = orderJpaRepository.findByIdOrNull(orderId.value)
        return orderEntity?.let { orderDataAccessMapper.orderEntityToOrder(it) }
    }
}