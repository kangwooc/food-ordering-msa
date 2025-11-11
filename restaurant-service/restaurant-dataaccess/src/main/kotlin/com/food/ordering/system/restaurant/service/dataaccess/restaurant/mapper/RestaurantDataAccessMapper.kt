package com.food.ordering.system.restaurant.service.dataaccess.restaurant.mapper

import com.food.ordering.system.dataaccess.restaurant.entity.RestaurantEntity
import com.food.ordering.system.dataaccess.restaurant.exception.RestaurantDataAccessException
import com.food.ordering.system.domain.valueobject.*
import com.food.ordering.system.restaurant.service.dataaccess.restaurant.entity.OrderApprovalEntity
import com.food.ordering.system.restaurant.service.domain.entity.OrderApproval
import com.food.ordering.system.restaurant.service.domain.entity.OrderDetail
import com.food.ordering.system.restaurant.service.domain.entity.Product
import com.food.ordering.system.restaurant.service.domain.entity.Restaurant
import org.springframework.stereotype.Component
import java.util.*

@Component
class RestaurantDataAccessMapper {
    fun orderApprovalToOrderApprovalEntity(orderApproval: OrderApproval): OrderApprovalEntity {
        return OrderApprovalEntity(
            restaurantId = orderApproval.restaurantId.value,
            orderId = orderApproval.orderId.value,
            status = orderApproval.orderApprovalStatus
        )
    }

    fun orderApprovalEntityToOrderApproval(orderApprovalEntity: OrderApprovalEntity): OrderApproval {
        return OrderApproval(
            restaurantId = RestaurantId(orderApprovalEntity.restaurantId!!),
            orderId = OrderId(orderApprovalEntity.orderId!!),
            orderApprovalStatus = orderApprovalEntity.status
        )
    }

    fun restaurantToRestaurantProducts(restaurant: Restaurant): List<UUID> {
        return restaurant.orderDetail.products.map { it.id.value }
    }

    fun restaurantEntityToRestaurant(restaurantEntities: List<RestaurantEntity>): Restaurant {
        val restaurantEntity = restaurantEntities.firstOrNull() ?:
        throw RestaurantDataAccessException("Restaurant could not be found!")

        val restaurantProducts = restaurantEntities.map {
            Product(
                name = it.productName,
                price = Money(it.productPrice),
                available = it.productAvailable,
                quantity = 0, // TODO: Quantity will be set later
            ).apply { id = ProductId(it.productId!!) }
        }

        return Restaurant(
            orderDetail = OrderDetail(
                products = restaurantProducts,
            ),
            active = restaurantEntity.restaurantActive,
            orderApproval = OrderApproval(
                restaurantId = RestaurantId(restaurantEntity.restaurantId!!),
                orderId = OrderId(UUID.randomUUID()),
                orderApprovalStatus = null
            )
        )
    }
}