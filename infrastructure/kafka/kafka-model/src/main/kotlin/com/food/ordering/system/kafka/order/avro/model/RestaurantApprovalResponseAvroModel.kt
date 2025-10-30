package com.food.ordering.system.kafka.order.avro.model

import com.github.avrokotlin.avro4k.`internal`.AvroGenerated
import com.github.avrokotlin.avro4k.serializer.InstantSerializer
import com.github.avrokotlin.avro4k.serializer.UUIDSerializer
import java.time.Instant
import java.util.UUID
import kotlin.String
import kotlin.collections.List
import kotlin.collections.emptyList
import kotlinx.serialization.Serializable

@Serializable
@AvroGenerated("""{"type":"record","name":"RestaurantApprovalResponseAvroModel","namespace":"com.food.ordering.system.kafka.order.avro.model","fields":[{"name":"id","type":{"type":"string","logicalType":"uuid"}},{"name":"sagaId","type":{"type":"string","logicalType":"uuid"}},{"name":"restaurantId","type":{"type":"string","logicalType":"uuid"}},{"name":"orderId","type":{"type":"string","logicalType":"uuid"}},{"name":"createdAt","type":{"type":"long","logicalType":"timestamp-millis"}},{"name":"orderApprovalStatus","type":{"type":"enum","name":"OrderApprovalStatus","symbols":["APPROVED","REJECTED"]}},{"name":"failureMessages","type":{"type":"array","items":"string"}}]}""")
data class RestaurantApprovalResponseAvroModel(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    @Serializable(with = UUIDSerializer::class)
    val sagaId: UUID,
    @Serializable(with = UUIDSerializer::class)
    val restaurantId: UUID,
    @Serializable(with = UUIDSerializer::class)
    val orderId: UUID,
    @Serializable(with = InstantSerializer::class)
    val createdAt: Instant,
    val orderApprovalStatus: OrderApprovalStatus,
    val failureMessages: List<String> = emptyList(),
)
