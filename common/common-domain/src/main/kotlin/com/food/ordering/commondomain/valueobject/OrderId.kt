package com.food.ordering.commondomain.valueobject

import java.util.UUID

class OrderId(
    val value: UUID
): BaseId<UUID>(value)