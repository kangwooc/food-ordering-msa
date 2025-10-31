package com.food.ordering.system.order.service.dataaccess.customer.mapper

import com.food.ordering.commondomain.valueobject.CustomerId
import com.food.ordering.system.order.service.domain.entity.Customer
import com.food.ordering.system.order.service.dataaccess.customer.entity.CustomerEntity
import org.springframework.stereotype.Component

@Component
class CustomerDataAccessMapper {
    fun customerEntityToCustomer(customerEntity: CustomerEntity): com.food.ordering.system.order.service.domain.entity.Customer {
        val customer = Customer()
        customer.id = CustomerId(customerEntity.id)
        return customer
    }
}