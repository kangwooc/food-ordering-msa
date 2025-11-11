package com.food.ordering.system.restaurant.service.domain.mapper

import com.food.ordering.system.domain.valueobject.Money
import com.food.ordering.system.domain.valueobject.OrderId
import com.food.ordering.system.domain.valueobject.OrderStatus
import com.food.ordering.system.domain.valueobject.RestaurantId
import com.food.ordering.system.restaurant.service.domain.dto.RestaurantApprovalRequest
import com.food.ordering.system.restaurant.service.domain.entity.OrderApproval
import com.food.ordering.system.restaurant.service.domain.entity.OrderDetail
import com.food.ordering.system.restaurant.service.domain.entity.Restaurant
import org.springframework.stereotype.Component
import java.util.*

@Component
class RestaurantDataMapper {
    fun restaurantApprovalRequestToRestaurant(restaurantApprovalRequest: RestaurantApprovalRequest): Restaurant {
         return Restaurant(
             orderApproval = OrderApproval(
                    orderId = OrderId(UUID.fromString(restaurantApprovalRequest.orderId)),
                    restaurantId = RestaurantId(UUID.fromString(restaurantApprovalRequest.restaurantId)),
             ),
             active = true,
             orderDetail = OrderDetail(
                 orderStatus = OrderStatus.valueOf(restaurantApprovalRequest.restaurantOrderStatus.name),
                 totalAmount = Money(restaurantApprovalRequest.price),
                 products = restaurantApprovalRequest.products
             )
         )
    }
}