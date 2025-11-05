package com.food.ordering.system.payment.service.dataaccess.creditentry.mapper

import com.food.ordering.system.domain.valueobject.CustomerId
import com.food.ordering.system.domain.valueobject.Money
import com.food.ordering.system.payment.service.dataaccess.creditentry.entity.CreditEntryEntity
import com.food.ordering.system.payment.service.domain.entity.CreditEntry
import com.food.ordering.system.payment.service.domain.valueobject.CreditEntryId
import org.springframework.stereotype.Component

@Component
class CreditEntryDataAccessMapper {
    fun creditEntryToCreditEntryEntity(creditEntry: CreditEntry): CreditEntryEntity {
        return CreditEntryEntity(
            id = creditEntry.id.value,
            customerId = creditEntry.customerId.value,
            totalCreditAmount = creditEntry.totalCreditAmount.amount,
        )
    }

    fun creditEntryEntityToCreditEntry(creditEntryEntity: CreditEntryEntity): CreditEntry {
        return CreditEntry(
            customerId = CustomerId(creditEntryEntity.customerId),
            totalCreditAmount = Money(creditEntryEntity.totalCreditAmount),
            creditEntryId = CreditEntryId(creditEntryEntity.id),
        ).apply {
            this.id = CreditEntryId(creditEntryEntity.id)
        }
    }
}