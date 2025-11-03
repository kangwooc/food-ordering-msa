package com.food.ordering.system.payment.service.domain.entity

import com.food.ordering.commondomain.entity.BaseEntity
import com.food.ordering.commondomain.valueobject.CustomerId
import com.food.ordering.commondomain.valueobject.Money
import com.food.ordering.system.payment.service.domain.valueobject.CreditHistoryId
import com.food.ordering.system.payment.service.domain.valueobject.TransactionType

class CreditHistory(
    val customerId: CustomerId,
    val amount: Money,
    val transactionType: TransactionType
): BaseEntity<CreditHistoryId> {
}