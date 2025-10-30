package com.food.ordering.system.kafka.order.avro.model

import com.github.avrokotlin.avro4k.`internal`.AvroGenerated
import com.github.avrokotlin.avro4k.serializer.BigDecimalSerializer
import com.github.avrokotlin.avro4k.serializer.InstantSerializer
import com.github.avrokotlin.avro4k.serializer.UUIDSerializer
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID
import kotlin.collections.List
import kotlin.collections.emptyList
import kotlinx.serialization.Serializable

@Serializable
@AvroGenerated("""{"type":"record","name":"RestaurantApprovalRequestAvroModel","namespace":"com.food.ordering.system.kafka.order.avro.model","fields":[{"name":"id","type":{"type":"string","logicalType":"uuid"}},{"name":"sagaId","type":{"type":"string","logicalType":"uuid"}},{"name":"restaurantId","type":{"type":"string","logicalType":"uuid"}},{"name":"orderId","type":{"type":"string","logicalType":"uuid"}},{"name":"restaurantOrderStatus","type":{"type":"enum","name":"RestaurantOrderStatus","symbols":["PAID"]}},{"name":"products","type":{"type":"array","items":{"type":"record","name":"Product","fields":[{"name":"id","type":"string","logicalType":"uuid"},{"name":"quantity","type":"int"}]}}},{"name":"price","type":{"type":"bytes","logicalType":"decimal","precision":10,"scale":2}},{"name":"createdAt","type":{"type":"long","logicalType":"timestamp-millis"}}]}""")
data class RestaurantApprovalRequestAvroModel(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    @Serializable(with = UUIDSerializer::class)
    val sagaId: UUID,
    @Serializable(with = UUIDSerializer::class)
    val restaurantId: UUID,
    @Serializable(with = UUIDSerializer::class)
    val orderId: UUID,
    val restaurantOrderStatus: RestaurantOrderStatus,
    val products: List<Product> = emptyList(),
    @Serializable(with = BigDecimalSerializer::class)
    val price: BigDecimal,
    @Serializable(with = InstantSerializer::class)
    val createdAt: Instant,
)
