package com.food.ordering.system.restaurant.service.messaging.publisher.kafka

import com.food.ordering.system.kafka.order.avro.model.RestaurantApprovalResponseAvroModel
import com.food.ordering.system.kafka.producer.KafkaMessageHelper
import com.food.ordering.system.kafka.producer.service.KafkaProducer
import com.food.ordering.system.restaurant.service.domain.config.RestaurantServiceConfigData
import com.food.ordering.system.restaurant.service.domain.event.OrderApprovedEvent
import com.food.ordering.system.restaurant.service.domain.ports.output.message.publisher.OrderApprovedMessagePublisher
import com.food.ordering.system.restaurant.service.messaging.mapper.RestaurantMessagingDataMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class OrderApprovedKafkaPublisher(
    private val restaurantMessagingDataMapper: RestaurantMessagingDataMapper,
    private val kafkaProducer: KafkaProducer<String, RestaurantApprovalResponseAvroModel>,
    private val restaurantServiceConfigData: RestaurantServiceConfigData,
    private val kafkaMessageHelper: KafkaMessageHelper,
): OrderApprovedMessagePublisher {
    private val logger = LoggerFactory.getLogger(OrderApprovedKafkaPublisher::class.java)

    override fun publish(domainEvent: OrderApprovedEvent) {
        val orderId = domainEvent.orderApproval.orderId.value.toString()
        logger.info("Publishing order approved")

        try {
            val restaurantApprovalResponseAvroModel =
                restaurantMessagingDataMapper.orderApprovedEventToRestaurantApprovalResponseAvroModel(domainEvent)

            kafkaProducer.send(
                restaurantServiceConfigData.restaurantApprovalResponseTopicName!!,
                orderId,
                restaurantApprovalResponseAvroModel,
                kafkaMessageHelper.getKafkaCallback(
                    restaurantServiceConfigData.restaurantApprovalResponseTopicName!!,
                    restaurantApprovalResponseAvroModel,
                    orderId
                )
            )

            logger.info("Order approved event sent to kafka for order id: $orderId")
        } catch (e: Exception) {
            logger.error("Error while sending order approved event to kafka for order id: ${domainEvent.orderApproval.orderId.value} ${e.message}")
        }
    }
}