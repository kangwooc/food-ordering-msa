package com.food.ordering.orderapplicationservice.ports.output.repository

import com.food.ordering.orderdomaincore.entity.Customer
import java.util.UUID

interface CustomerRepository {
    fun findCustomer(customerId: UUID): Customer?
}