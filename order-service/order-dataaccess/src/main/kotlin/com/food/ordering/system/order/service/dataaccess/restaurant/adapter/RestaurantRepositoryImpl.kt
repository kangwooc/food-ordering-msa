package com.ordering.system.order.service.dataaccess.restaurant.adapter

import com.food.ordering.orderapplicationservice.ports.output.repository.RestaurantRepository
import com.food.ordering.orderdomaincore.entity.Restaurant
import com.ordering.system.order.service.dataaccess.restaurant.mapper.RestaurantDataAccessMapper
import com.ordering.system.order.service.dataaccess.restaurant.repository.RestaurantJpaRepository
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