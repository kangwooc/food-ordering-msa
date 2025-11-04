@file:OptIn(
    InternalAvro4kApi::class,
    ExperimentalAvro4kApi::class,
)

package com.food.ordering.system.kafka.order.avro.model

import com.github.avrokotlin.avro4k.ExperimentalAvro4kApi
import com.github.avrokotlin.avro4k.InternalAvro4kApi
import com.github.avrokotlin.avro4k.`internal`.AvroGenerated
import com.github.avrokotlin.avro4k.serializer.UUIDSerializer
import java.util.UUID
import kotlin.OptIn
import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
@AvroGenerated("""{"type":"record","name":"CustomerAvroModel","namespace":"com.food.ordering.system.kafka.order.avro.model","fields":[{"name":"id","type":{"type":"string","logicalType":"uuid"}},{"name":"username","type":"string"},{"name":"firstName","type":"string"},{"name":"lastName","type":"string"}]}""")
public data class CustomerAvroModel(
    @Serializable(with = UUIDSerializer::class)
    public val id: UUID,
    public val username: String,
    public val firstName: String,
    public val lastName: String,
)
