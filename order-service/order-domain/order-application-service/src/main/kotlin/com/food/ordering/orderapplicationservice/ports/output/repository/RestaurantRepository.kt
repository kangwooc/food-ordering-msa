package com.food.ordering.orderapplicationservice.ports.output.repository

import com.food.ordering.orderdomaincore.entity.Restaurant

interface RestaurantRepository {
    fun findRestaurantInformation(restaurant: Restaurant): Restaurant?

}