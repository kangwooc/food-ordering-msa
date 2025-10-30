package com.food.ordering.ordercontainer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories(basePackages = ["com.food.ordering.system.order.service.dataaccess"])
@EntityScan(basePackages = ["com.food.ordering.system.order.service.dataaccess"])
@SpringBootApplication(scanBasePackages = ["com.food.ordering.system"])
class OrderContainerApplication

fun main(args: Array<String>) {
    runApplication<OrderContainerApplication>(*args)
}
