package com.ordering.system.order.service.dataaccess.customer.repository

import com.ordering.system.order.service.dataaccess.customer.entity.CustomerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CustomerJpaRepository: JpaRepository<CustomerEntity, UUID> {
}