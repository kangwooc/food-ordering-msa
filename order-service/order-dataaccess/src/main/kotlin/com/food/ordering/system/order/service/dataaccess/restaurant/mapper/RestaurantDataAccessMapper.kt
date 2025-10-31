package com.food.ordering.system.order.service.dataaccess.restaurant.mapper

import com.food.ordering.commondomain.valueobject.Money
import com.food.ordering.commondomain.valueobject.ProductId
import com.food.ordering.system.order.service.dataaccess.restaurant.entity.RestaurantEntity
import com.food.ordering.system.order.service.dataaccess.restaurant.exception.RestaurantDataAccessException
import com.food.ordering.system.order.service.domain.entity.Restaurant
import org.springframework.stereotype.Component
import java.util.*

@Component
class RestaurantDataAccessMapper {
    fun restaurantToRestaurantProducts(restaurant: Restaurant): List<UUID> {
        return restaurant.products.map { it.productId.value }
    }

    fun restaurantEntityToRestaurant(restaurantEntities: List<RestaurantEntity>): Restaurant {
        val restaurantEntity = restaurantEntities.firstOrNull() ?:
            throw RestaurantDataAccessException("Restaurant could not be found!")

        val restaurantProducts = restaurantEntities.map {
            com.food.ordering.system.order.service.domain.entity.Product(
                productId = ProductId(it.productId!!),
                name = it.productName,
                price = Money(it.productPrice)
            )
        }
        
        return Restaurant(
            products = restaurantProducts,
            active = restaurantEntity.restaurantActive
        )
    }
}