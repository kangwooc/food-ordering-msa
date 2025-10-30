package com.food.ordering.system.order.service.domain.valueobject

import com.food.ordering.commondomain.valueobject.BaseId
import java.util.UUID

class TrackingId(value: UUID): BaseId<UUID>(value)
