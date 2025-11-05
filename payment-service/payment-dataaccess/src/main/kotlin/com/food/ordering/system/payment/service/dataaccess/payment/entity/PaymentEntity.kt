package com.food.ordering.system.payment.service.dataaccess.payment.entity

import com.food.ordering.system.domain.valueobject.PaymentStatus
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*

@Table(name = "payments")
@Entity
class PaymentEntity(
    @Id
    var id: UUID,
    var customerId: UUID,
    var orderId: UUID,
    var price: BigDecimal,
    @Enumerated(EnumType.STRING)
    var paymentStatus: PaymentStatus,
    var createdAt: ZonedDateTime,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PaymentEntity

        return id == other.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }
}
