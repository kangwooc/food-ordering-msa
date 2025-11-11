package com.food.ordering.system.restaurant.service.messaging.listener.kafka

import com.food.ordering.system.kafka.consumer.KafkaConsumer
import com.food.ordering.system.kafka.order.avro.model.RestaurantApprovalRequestAvroModel
import com.food.ordering.system.restaurant.service.domain.ports.input.message.listener.RestaurantApprovalRequestMessageListener
import com.food.ordering.system.restaurant.service.messaging.mapper.RestaurantMessagingDataMapper
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class RestaurantApprovalRequestKafkaListener(
    private val restaurantApprovalRequestMessageListener: RestaurantApprovalRequestMessageListener,
    private val restaurantMessagingDataMapper: RestaurantMessagingDataMapper,
) : KafkaConsumer<RestaurantApprovalRequestAvroModel> {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @KafkaListener(
        id = "\${kafka-consumer-config.restaurant-approval-consumer-group-id}",
        topics = ["\${restaurant-service.restaurant-approval-request-topic-name}"]
    )
    override fun receive(
        @Payload messages: List<RestaurantApprovalRequestAvroModel>,
        @Header(KafkaHeaders.RECEIVED_KEY) key: List<String>,
        @Header(KafkaHeaders.RECEIVED_PARTITION) partition: List<Int>,
        @Header(KafkaHeaders.OFFSET) offsets: List<Long>
    ) {
        logger.info("Received ${messages.size} restaurant approval requests with keys $key., partitions $partition and offsets $offsets")

        messages.forEach { restaurantApprovalRequestAvroModel ->
            try {
                logger.info("Received ${restaurantApprovalRequestAvroModel.orderId} restaurant approval requests")
                restaurantApprovalRequestMessageListener.approveOrder(
                    restaurantMessagingDataMapper.restaurantApprovalRequestAvroModelToRestaurantApprovalRequest(
                        restaurantApprovalRequestAvroModel
                    )
                )
            } catch (e: Exception) {
                logger.error(
                    "Error while processing restaurant approval request " +
                            "$restaurantApprovalRequestAvroModel, ${e.message}"
                )
            }
        }
    }
}