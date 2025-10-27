package com.food.ordering.commondomain.valueobject

import java.util.UUID

class OrderId(
    value: UUID
): BaseId<UUID>(value)