package com.food.ordering.system.order.service.domain

import com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.restaurantapproval.OrderPaidRestaurantRequestMessagePublisher
import com.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository
import com.food.ordering.system.order.service.domain.ports.output.repository.RestaurantRepository
import org.mockito.Mockito
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication(
    scanBasePackages = [
        "com.food.ordering.system.order.service.domain",
        "com.food.ordering.commondomain"
    ])
class OrderTestConfiguration {
    @Bean
    fun orderCreatedPaymentRequestMessagePublisher(): OrderCreatedPaymentRequestMessagePublisher {
        return Mockito.mock(OrderCreatedPaymentRequestMessagePublisher::class.java)
    }

    @Bean
    fun orderCancelledPaymentRequestMessagePublisher(): OrderCreatedPaymentRequestMessagePublisher {
        return Mockito.mock(OrderCreatedPaymentRequestMessagePublisher::class.java)
    }

    @Bean
    fun orderPaidRestaurantRequestMessagePublisher(): OrderPaidRestaurantRequestMessagePublisher {
        return Mockito.mock(OrderPaidRestaurantRequestMessagePublisher::class.java)
    }
    @Bean
    fun orderRepository(): OrderRepository {
        return Mockito.mock(OrderRepository::class.java)
    }

    @Bean
    fun customerRepository(): CustomerRepository {
        return Mockito.mock(CustomerRepository::class.java)
    }

    @Bean
    fun restaurantRepository(): RestaurantRepository {
        return Mockito.mock(RestaurantRepository::class.java)
    }

    @Bean
    fun orderDomainService(): com.food.ordering.system.order.service.domain.OrderDomainService {
        return OrderDomainServiceImpl()
    }
}