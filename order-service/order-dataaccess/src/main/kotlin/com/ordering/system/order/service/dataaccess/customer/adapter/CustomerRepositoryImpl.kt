package com.ordering.system.order.service.dataaccess.customer.adapter

import com.food.ordering.orderapplicationservice.ports.output.repository.CustomerRepository
import com.food.ordering.orderdomaincore.entity.Customer
import com.ordering.system.order.service.dataaccess.customer.mapper.CustomerDataAccessMapper
import com.ordering.system.order.service.dataaccess.customer.repository.CustomerJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.*

@Component
class CustomerRepositoryImpl(
    private val customerJpaRepository: CustomerJpaRepository,
    private val customerDataAccessMapper: CustomerDataAccessMapper
): CustomerRepository {
    override fun findCustomer(customerId: UUID): Customer? {
        return customerJpaRepository.findByIdOrNull(customerId)?.let {
            customerDataAccessMapper.customerEntityToCustomer(it)
        }
    }
}