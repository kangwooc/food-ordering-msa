@file:OptIn(
    InternalAvro4kApi::class,
    ExperimentalAvro4kApi::class,
)

package com.food.ordering.system.kafka.order.avro.model

import com.github.avrokotlin.avro4k.ExperimentalAvro4kApi
import com.github.avrokotlin.avro4k.InternalAvro4kApi
import com.github.avrokotlin.avro4k.`internal`.AvroGenerated
import kotlin.OptIn
import kotlinx.serialization.Serializable

@Serializable
@AvroGenerated("""{"type":"enum","name":"PaymentOrderStatus","namespace":"com.food.ordering.system.kafka.order.avro.model","symbols":["PENDING","CANCELLED"]}""")
public enum class PaymentOrderStatus {
    PENDING,
    CANCELLED,
}
