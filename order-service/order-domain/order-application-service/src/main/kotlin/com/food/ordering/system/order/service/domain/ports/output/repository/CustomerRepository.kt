package com.food.ordering.orderapplicationservice.ports.output.repository

import com.food.ordering.system.order.service.domain.entity.Customer
import java.util.UUID

interface CustomerRepository {
    fun findCustomer(customerId: UUID): com.food.ordering.system.order.service.domain.entity.Customer?
}