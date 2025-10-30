package com.ordering.system.order.service.dataaccess.customer.mapper

import com.food.ordering.commondomain.valueobject.CustomerId
import com.food.ordering.orderdomaincore.entity.Customer
import com.ordering.system.order.service.dataaccess.customer.entity.CustomerEntity
import org.springframework.stereotype.Component

@Component
class CustomerDataAccessMapper {
    fun customerEntityToCustomer(customerEntity: CustomerEntity): Customer {
        val customer = Customer()
        customer.id = CustomerId(customerEntity.id)
        return customer
    }
}