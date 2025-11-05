package com.food.ordering.system.order.service.messaging.publisher.kafka

import com.food.ordering.system.kafka.producer.service.KafkaProducer
import com.food.ordering.system.kafka.order.avro.model.RestaurantApprovalRequestAvroModel
import com.food.ordering.system.kafka.producer.KafkaMessageHelper
import com.food.ordering.system.order.service.domain.config.OrderServiceConfigData
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.restaurantapproval.OrderPaidRestaurantRequestMessagePublisher
import com.food.ordering.system.order.service.messaging.mapper.OrderMessagingDataMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class PayOrderKafkaMessagePublisher(
    private val kafkaMessageHelper: KafkaMessageHelper,
    private val orderMessagingDataMapper: OrderMessagingDataMapper,
    private val orderServiceConfigData: OrderServiceConfigData,
    private val kafkaProducer: KafkaProducer<String, RestaurantApprovalRequestAvroModel>
): OrderPaidRestaurantRequestMessagePublisher {
    private val logger = LoggerFactory.getLogger(PayOrderKafkaMessagePublisher::class.java)

    override fun publish(domainEvent: com.food.ordering.system.order.service.domain.event.OrderPaidEvent) {
        val orderId = domainEvent.order.id.value.toString()
        logger.info("Received OrderPaidEvent for order id: $orderId")
        try {
            val restaurantApprovalRequestAvroModel = orderMessagingDataMapper.orderPaidEventToRestaurantApprovalRequestAvroModel(domainEvent)
            kafkaProducer.send(
                orderServiceConfigData.restaurantApprovalRequestTopicName!!,
                orderId,
                restaurantApprovalRequestAvroModel,
                kafkaMessageHelper.getKafkaCallback(
                    orderServiceConfigData.restaurantApprovalRequestTopicName!!,
                    restaurantApprovalRequestAvroModel,
                    orderId
                )
            )
            logger.info("Published RestaurantApprovalRequestAvroModel for order id: $orderId")
        } catch (ex: Exception) {
            logger.error(
                "Error while sending RestaurantApprovalRequestAvroModel message to Kafka for order id: $orderId, error: ${ex.message}",
                ex
            )
            throw ex
        }
    }
}