package com.food.ordering.system.kafka.order.avro.model

import com.github.avrokotlin.avro4k.`internal`.AvroGenerated
import com.github.avrokotlin.avro4k.serializer.BigDecimalSerializer
import com.github.avrokotlin.avro4k.serializer.InstantSerializer
import com.github.avrokotlin.avro4k.serializer.UUIDSerializer
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID
import kotlinx.serialization.Serializable

@Serializable
@AvroGenerated("""{"type":"record","name":"PaymentRequestAvroModel","namespace":"com.food.ordering.system.kafka.order.avro.model","fields":[{"name":"id","type":{"type":"string","logicalType":"uuid"}},{"name":"sagaId","type":{"type":"string","logicalType":"uuid"}},{"name":"customerId","type":{"type":"string","logicalType":"uuid"}},{"name":"orderId","type":{"type":"string","logicalType":"uuid"}},{"name":"price","type":{"type":"bytes","logicalType":"decimal","precision":10,"scale":2}},{"name":"createdAt","type":{"type":"long","logicalType":"timestamp-millis"}},{"name":"paymentOrderStatus","type":{"type":"enum","name":"PaymentOrderStatus","symbols":["PENDING","CANCELLED"]}}]}""")
public data class PaymentRequestAvroModel(
    @Serializable(with = UUIDSerializer::class)
    public val id: UUID,
    @Serializable(with = UUIDSerializer::class)
    public val sagaId: UUID,
    @Serializable(with = UUIDSerializer::class)
    public val customerId: UUID,
    @Serializable(with = UUIDSerializer::class)
    public val orderId: UUID,
    @Serializable(with = BigDecimalSerializer::class)
    public val price: BigDecimal,
    @Serializable(with = InstantSerializer::class)
    public val createdAt: Instant,
    public val paymentOrderStatus: PaymentOrderStatus,
)
