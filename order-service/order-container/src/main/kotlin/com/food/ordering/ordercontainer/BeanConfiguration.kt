package com.food.ordering.ordercontainer

import com.food.ordering.system.order.service.domain.OrderDomainService
import com.food.ordering.orderdomaincore.OrderDomainServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfiguration {
    @Bean
    fun orderDomainService(): com.food.ordering.system.order.service.domain.OrderDomainService {
        return OrderDomainServiceImpl()
    }
}