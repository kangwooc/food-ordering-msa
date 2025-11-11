package com.food.ordering.system.restaurant.service.dataaccess.restaurant.adapter

import com.food.ordering.system.restaurant.service.dataaccess.restaurant.mapper.RestaurantDataAccessMapper
import com.food.ordering.system.restaurant.service.dataaccess.restaurant.repository.OrderApprovalJpaRepository
import com.food.ordering.system.restaurant.service.domain.entity.OrderApproval
import com.food.ordering.system.restaurant.service.domain.ports.output.repository.OrderApprovalRepository
import org.springframework.stereotype.Component

@Component
class OrderApprovalRepositoryImpl(
    private val orderApprovalJpaRepository: OrderApprovalJpaRepository,
    private val restaurantDataAccessMapper: RestaurantDataAccessMapper
): OrderApprovalRepository {
    override fun save(orderApproval: OrderApproval): OrderApproval {
        val orderApprovalEntity = restaurantDataAccessMapper.orderApprovalToOrderApprovalEntity(orderApproval)
        val savedOrderApprovalEntity = orderApprovalJpaRepository.save(orderApprovalEntity)
        return restaurantDataAccessMapper.orderApprovalEntityToOrderApproval(savedOrderApprovalEntity)
    }
}