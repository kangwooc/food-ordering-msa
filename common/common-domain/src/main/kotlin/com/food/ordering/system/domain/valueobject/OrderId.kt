package com.food.ordering.system.domain.valueobject

import java.util.UUID

class OrderId(
    value: UUID
): BaseId<UUID>(value)