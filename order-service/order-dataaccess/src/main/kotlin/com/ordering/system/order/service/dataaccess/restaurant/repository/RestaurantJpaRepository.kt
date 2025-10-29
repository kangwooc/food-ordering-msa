package com.ordering.system.order.service.dataaccess.restaurant.repository

import com.ordering.system.order.service.dataaccess.restaurant.entity.RestaurantEntity
import com.ordering.system.order.service.dataaccess.restaurant.entity.RestaurantEntityId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RestaurantJpaRepository: JpaRepository<RestaurantEntity, RestaurantEntityId> {
    fun findByRestaurantIdAndProductIdIn(restaurantId: UUID, productId: List<UUID>): List<RestaurantEntity>?
}