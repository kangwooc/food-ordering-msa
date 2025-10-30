package com.food.ordering.system.order.service.dataaccess.restaurant.adapter

import com.food.ordering.system.order.service.dataaccess.restaurant.mapper.RestaurantDataAccessMapper
import com.food.ordering.system.order.service.dataaccess.restaurant.repository.RestaurantJpaRepository
import com.food.ordering.system.order.service.domain.entity.Restaurant
import com.food.ordering.system.order.service.domain.ports.output.repository.RestaurantRepository
import org.springframework.stereotype.Component

@Component
class RestaurantRepositoryImpl(
    private val restaurantJpaRepository: RestaurantJpaRepository,
    private val restaurantDataAccessMapper: RestaurantDataAccessMapper
): RestaurantRepository {
    override fun findRestaurantInformation(restaurant: Restaurant): Restaurant? {
        val restaurantProducts = restaurantDataAccessMapper.restaurantToRestaurantProducts(restaurant)
        return restaurantJpaRepository.findByRestaurantIdAndProductIdIn(restaurant.id.value, restaurantProducts)?.let {
            return restaurantDataAccessMapper.restaurantEntityToRestaurant(it)
        }
    }
}