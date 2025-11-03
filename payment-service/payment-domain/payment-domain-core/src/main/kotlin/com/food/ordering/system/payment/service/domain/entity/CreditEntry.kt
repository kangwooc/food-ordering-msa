package com.food.ordering.system.payment.service.domain.entity

import com.food.ordering.commondomain.entity.BaseEntity
import com.food.ordering.commondomain.valueobject.CustomerId
import com.food.ordering.commondomain.valueobject.Money
import com.food.ordering.system.payment.service.domain.valueobject.CreditEntryId

class CreditEntry(
    val creditEntryId: CreditEntryId,
    var customerId: CustomerId,
    var totalCreditAmount: Money,
): BaseEntity<CreditEntryId>() {
    fun addCreditAmount(amount: Money) {
        totalCreditAmount = totalCreditAmount.plus(amount)
    }

    fun subtractCreditAmount(amount: Money) {
        totalCreditAmount = totalCreditAmount.minus(amount)
    }
}