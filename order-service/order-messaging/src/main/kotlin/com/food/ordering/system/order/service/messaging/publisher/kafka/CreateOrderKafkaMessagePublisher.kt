package com.food.ordering.system.order.service.messaging.publisher.kafka

import com.food.ordering.orderapplicationservice.config.OrderServiceConfigData
import com.food.ordering.orderapplicationservice.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher
import com.food.ordering.orderdomaincore.event.OrderCreatedEvent
import com.food.ordering.service.system.kafka.producer.service.KafkaProducer
import com.food.ordering.system.kafka.order.avro.model.PaymentRequestAvroModel
import com.food.ordering.system.order.service.messaging.mapper.OrderMessagingDataMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class CreateOrderKafkaMessagePublisher(
    private val orderMessagingDataMapper: OrderMessagingDataMapper,
    private val orderServiceConfigData: OrderServiceConfigData,
    private val kafkaProducer: KafkaProducer<String, PaymentRequestAvroModel>,
    private val orderKafkaMessageHelper: OrderKafkaMessageHelper
) : OrderCreatedPaymentRequestMessagePublisher {

    private val logger = LoggerFactory.getLogger(CreateOrderKafkaMessagePublisher::class.java)

    override fun publish(domainEvent: OrderCreatedEvent) {
        val orderId = domainEvent.order.id.value.toString()
        logger.info("Received OrderCreatedEvent for order id: $orderId")
        try {
            val paymentRequestAvroModel = orderMessagingDataMapper.orderCreatedEventToOrderResponse(domainEvent)
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
            logger.error(
                "Error while sending PaymentRequestAvroModel message to Kafka for order id: $orderId, error: ${ex.message}",
                ex
            )
            throw ex
        }

    }
}