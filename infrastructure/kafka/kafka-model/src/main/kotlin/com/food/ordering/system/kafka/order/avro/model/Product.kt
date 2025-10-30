package com.food.ordering.system.kafka.order.avro.model

import com.github.avrokotlin.avro4k.AvroProp
import kotlin.Int
import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public data class Product(
    @AvroProp("logicalType", "uuid")
    public val id: String,
    public val quantity: Int,
)
