@file:OptIn(
    InternalAvro4kApi::class,
    ExperimentalAvro4kApi::class,
)

package com.food.ordering.system.kafka.order.avro.model

import com.github.avrokotlin.avro4k.AvroProp
import com.github.avrokotlin.avro4k.ExperimentalAvro4kApi
import com.github.avrokotlin.avro4k.InternalAvro4kApi
import com.github.avrokotlin.avro4k.`internal`.AvroGenerated
import kotlin.Int
import kotlin.OptIn
import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
@AvroGenerated("""{"type":"record","name":"Product","namespace":"com.food.ordering.system.kafka.order.avro.model","fields":[{"name":"id","type":"string","logicalType":"uuid"},{"name":"quantity","type":"int"}]}""")
public data class Product(
    @AvroProp("logicalType", "uuid")
    public val id: String,
    public val quantity: Int,
)
