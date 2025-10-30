package com.food.ordering.orderdomaincore.valueobject

import com.food.ordering.commondomain.valueobject.BaseId
import java.util.UUID

class TrackingId(value: UUID): BaseId<UUID>(value)
