package com.food.ordering.system.order.service.messaging.listener.kafka

import com.food.ordering.orderapplicationservice.ports.input.message.listener.restaurantapproval.RestaurantApprovalMessageListener
import com.food.ordering.system.kafka.consumer.KafkaConsumer
import com.food.ordering.system.kafka.order.avro.model.OrderApprovalStatus
import com.food.ordering.system.kafka.order.avro.model.RestaurantApprovalResponseAvroModel
import com.food.ordering.system.order.service.messaging.mapper.OrderMessagingDataMapper
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class RestaurantApprovalResponseKafkaListener(
    private val restaurantApprovalMessageListener: RestaurantApprovalMessageListener,
    private val orderMessagingDataMapper: OrderMessagingDataMapper
) : KafkaConsumer<RestaurantApprovalResponseAvroModel> {
    private val logger = LoggerFactory.getLogger(RestaurantApprovalResponseKafkaListener::class.java)

    @KafkaListener(
        id = "\${kafka-consumer-config.restaurant-approval-consumer-group-id}",
        topics = ["\${order-service.restaurant-approval-response-topic-name}"]
    )
    override fun receive(
        @Payload message: List<RestaurantApprovalResponseAvroModel>,
        @Header(KafkaHeaders.RECEIVED_KEY) key: List<String>,
        @Header(KafkaHeaders.RECEIVED_PARTITION) partition: List<Int>,
        @Header(KafkaHeaders.OFFSET) offsets: List<Long>
    ) {
        logger.info("Received ${message.size} restaurant approval responses, keys: $key, partitions: $partition, offsets: $offsets")
        message.forEach { restaurantApprovalRequestAvroModel ->
            if (restaurantApprovalRequestAvroModel.orderApprovalStatus == OrderApprovalStatus.APPROVED) {
                logger.info("Processing restaurant approval for order id: ${restaurantApprovalRequestAvroModel.orderId} with status: ${restaurantApprovalRequestAvroModel.orderApprovalStatus}")

                restaurantApprovalMessageListener.orderApproved(
                    orderMessagingDataMapper.restaurantApprovalResponseAvroModelToRestaurantApprovalResponse(
                        restaurantApprovalRequestAvroModel
                    )
                )
            } else if (restaurantApprovalRequestAvroModel.orderApprovalStatus == OrderApprovalStatus.REJECTED) {
                logger.info("Processing restaurant rejection for order id: ${restaurantApprovalRequestAvroModel.orderId} with status: ${restaurantApprovalRequestAvroModel.orderApprovalStatus}")
                restaurantApprovalMessageListener.orderRejected(
                    orderMessagingDataMapper.restaurantApprovalResponseAvroModelToRestaurantApprovalResponse(
                        restaurantApprovalRequestAvroModel
                    )
                )
            }
        }
    }
}