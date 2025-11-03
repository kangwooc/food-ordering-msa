package com.food.ordering.system.payment.service.domain.valueobject

import com.food.ordering.commondomain.valueobject.BaseId
import java.util.UUID

class CreditHistoryId(value: UUID) : BaseId<UUID>(value)
