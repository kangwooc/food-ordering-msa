package com.food.ordering.system.order.service.dataaccess.customer.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

// Customer M-VIEW Entity
@Table(name = "order_customer_m_view", schema = "customer")
@Entity
class CustomerEntity(
    @Id
    var id: UUID,
    var firstName: String,
    var lastName: String,
    var email: String,
)