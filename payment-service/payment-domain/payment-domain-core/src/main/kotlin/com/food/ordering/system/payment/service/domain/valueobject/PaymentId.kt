package com.food.ordering.system.payment.service.domain.valueobject

import com.food.ordering.system.domain.valueobject.BaseId
import java.util.UUID

class PaymentId(value: UUID) : BaseId<UUID>(value)