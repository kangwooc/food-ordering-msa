package com.food.ordering.system.dataaccess.restaurant.repository

import com.food.ordering.system.dataaccess.restaurant.entity.RestaurantEntity
import com.food.ordering.system.dataaccess.restaurant.entity.RestaurantEntityId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RestaurantJpaRepository: JpaRepository<RestaurantEntity, RestaurantEntityId> {
    fun findByRestaurantIdAndProductIdIn(restaurantId: UUID, productId: List<UUID>): List<RestaurantEntity>?
}