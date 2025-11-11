package com.food.ordering.system.restaurant.service.messaging.publisher.kafka

import com.food.ordering.system.kafka.order.avro.model.RestaurantApprovalRequestAvroModel
import com.food.ordering.system.kafka.order.avro.model.RestaurantApprovalResponseAvroModel
import com.food.ordering.system.kafka.producer.KafkaMessageHelper
import com.food.ordering.system.kafka.producer.service.KafkaProducer
import com.food.ordering.system.restaurant.service.domain.config.RestaurantServiceConfigData
import com.food.ordering.system.restaurant.service.domain.event.OrderRejectedEvent
import com.food.ordering.system.restaurant.service.domain.ports.output.message.publisher.OrderRejectedMessagePublisher
import com.food.ordering.system.restaurant.service.messaging.mapper.RestaurantMessagingDataMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class OrderRejectedKafkaPublisher(
    private val restaurantMessagingDataMapper: RestaurantMessagingDataMapper,
    private val kafkaProducer: KafkaProducer<String, RestaurantApprovalResponseAvroModel>,
    private val restaurantServiceConfigData: RestaurantServiceConfigData,
    private val kafkaMessageHelper: KafkaMessageHelper,
): OrderRejectedMessagePublisher {
    private val logger = LoggerFactory.getLogger(OrderRejectedKafkaPublisher::class.java)

    override fun publish(domainEvent: OrderRejectedEvent) {
        val orderId = domainEvent.orderApproval.orderId.value.toString()
        logger.info("Publishing order rejected")

        try {
            val restaurantApprovalResponseAvroModel =
                restaurantMessagingDataMapper.orderApprovedEventToRestaurantApprovalResponseAvroModel(domainEvent)

            kafkaProducer.send(
                restaurantServiceConfigData.restaurantApprovalRequestTopicName!!,
                orderId,
                restaurantApprovalResponseAvroModel,
                kafkaMessageHelper.getKafkaCallback(
                    restaurantServiceConfigData.restaurantApprovalRequestTopicName!!,
                    restaurantApprovalResponseAvroModel,
                    orderId
                )
            )

            logger.info("Order rejected event sent to kafka for order id: $orderId")
        } catch (e: Exception) {
            logger.error("Error while sending order rejected event to kafka for order id: ${domainEvent.orderApproval.orderId.value} ${e.message}")
        }
    }
}