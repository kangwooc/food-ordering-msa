package com.food.ordering.system.payment.service.dataaccess.credithistory.entity

import com.food.ordering.system.payment.service.domain.valueobject.TransactionType
import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "credit_history")
class CreditHistoryEntity(
    @Id
    var id: UUID,
    var customerId: UUID,
    var amount: BigDecimal,
    @Enumerated(EnumType.STRING)
    var type: TransactionType,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CreditHistoryEntity

        return id == other.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }
}