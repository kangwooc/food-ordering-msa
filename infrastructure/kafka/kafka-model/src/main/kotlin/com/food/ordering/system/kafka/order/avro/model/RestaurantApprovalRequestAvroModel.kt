@file:OptIn(
    InternalAvro4kApi::class,
    ExperimentalAvro4kApi::class,
)

package com.food.ordering.system.kafka.order.avro.model

import com.github.avrokotlin.avro4k.ExperimentalAvro4kApi
import com.github.avrokotlin.avro4k.InternalAvro4kApi
import com.github.avrokotlin.avro4k.`internal`.AvroGenerated
import com.github.avrokotlin.avro4k.serializer.BigDecimalSerializer
import com.github.avrokotlin.avro4k.serializer.InstantSerializer
import com.github.avrokotlin.avro4k.serializer.UUIDSerializer
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID
import kotlin.OptIn
import kotlin.collections.List
import kotlin.collections.emptyList
import kotlinx.serialization.Serializable

@Serializable
@AvroGenerated("""{"type":"record","name":"RestaurantApprovalRequestAvroModel","namespace":"com.food.ordering.system.kafka.order.avro.model","fields":[{"name":"id","type":{"type":"string","logicalType":"uuid"}},{"name":"sagaId","type":{"type":"string","logicalType":"uuid"}},{"name":"restaurantId","type":{"type":"string","logicalType":"uuid"}},{"name":"orderId","type":{"type":"string","logicalType":"uuid"}},{"name":"restaurantOrderStatus","type":{"type":"enum","name":"RestaurantOrderStatus","symbols":["PAID"]}},{"name":"products","type":{"type":"array","items":{"type":"record","name":"Product","fields":[{"name":"id","type":"string","logicalType":"uuid"},{"name":"quantity","type":"int"}]}}},{"name":"price","type":{"type":"bytes","logicalType":"decimal","precision":10,"scale":2}},{"name":"createdAt","type":{"type":"long","logicalType":"timestamp-millis"}}]}""")
public data class RestaurantApprovalRequestAvroModel(
    @Serializable(with = UUIDSerializer::class)
    public val id: UUID,
    @Serializable(with = UUIDSerializer::class)
    public val sagaId: UUID,
    @Serializable(with = UUIDSerializer::class)
    public val restaurantId: UUID,
    @Serializable(with = UUIDSerializer::class)
    public val orderId: UUID,
    public val restaurantOrderStatus: RestaurantOrderStatus,
    public val products: List<Product> = emptyList(),
    @Serializable(with = BigDecimalSerializer::class)
    public val price: BigDecimal,
    @Serializable(with = InstantSerializer::class)
    public val createdAt: Instant,
)
