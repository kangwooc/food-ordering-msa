package com.food.ordering.system.order.service.messaging.publisher.kafka

import com.food.ordering.orderapplicationservice.config.OrderServiceConfigData
import com.food.ordering.orderapplicationservice.ports.output.message.publisher.payment.OrderCancelledPaymentRequestMessagePublisher
import com.food.ordering.orderdomaincore.event.OrderCancelledEvent
import com.food.ordering.service.system.kafka.producer.service.KafkaProducer
import com.food.ordering.system.kafka.order.avro.model.PaymentRequestAvroModel
import com.food.ordering.system.order.service.messaging.mapper.OrderMessagingDataMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class CancelOrderKafkaMessagePublisher(
    private val orderMessagingDataMapper: OrderMessagingDataMapper,
    private val orderServiceConfigData: OrderServiceConfigData,
    private val kafkaProducer: KafkaProducer<String, PaymentRequestAvroModel>,
    private val orderKafkaMessageHelper: OrderKafkaMessageHelper
): OrderCancelledPaymentRequestMessagePublisher {
    private val logger = LoggerFactory.getLogger(CancelOrderKafkaMessagePublisher::class.java)

    override fun publish(domainEvent: OrderCancelledEvent) {
        val orderId = domainEvent.order.id.value.toString()
        logger.info("Received OrderCancelledEvent for order id: $orderId")

        try {
            val paymentRequestAvroModel = orderMessagingDataMapper.orderCancelledEventToOrderResponse(domainEvent)
            kafkaProducer.send(
                orderServiceConfigData.paymentRequestTopicName,
                orderId,
                paymentRequestAvroModel,
                orderKafkaMessageHelper.getKafkaCallback(
                    orderServiceConfigData.paymentRequestTopicName,
                    paymentRequestAvroModel,
                    orderId
                )
            )
            logger.info("Published PaymentRequestAvroModel for order id: $orderId")
        } catch (ex: Exception) {
            logger.error("Error while sending PaymentRequestAvroModel message to Kafka for order id: $orderId, error: ${ex.message}", ex)
            throw ex
        }
    }
}