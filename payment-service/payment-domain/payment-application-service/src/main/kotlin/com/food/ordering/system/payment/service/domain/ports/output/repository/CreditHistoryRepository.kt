package com.food.ordering.system.payment.service.domain.ports.output.repository

import com.food.ordering.system.domain.valueobject.CustomerId
import com.food.ordering.system.payment.service.domain.entity.CreditHistory

interface CreditHistoryRepository {
    fun save(creditHistory: CreditHistory): CreditHistory

    fun findByCustomerId(customerId: CustomerId): List<CreditHistory>?
}