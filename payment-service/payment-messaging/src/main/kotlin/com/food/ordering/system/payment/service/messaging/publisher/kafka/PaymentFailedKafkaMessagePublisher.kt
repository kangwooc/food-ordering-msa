package com.food.ordering.system.payment.service.messaging.publisher.kafka

import com.food.ordering.system.kafka.order.avro.model.PaymentResponseAvroModel
import com.food.ordering.system.kafka.producer.KafkaMessageHelper
import com.food.ordering.system.kafka.producer.service.KafkaProducer
import com.food.ordering.system.payment.service.domain.config.PaymentServiceConfigData
import com.food.ordering.system.payment.service.domain.event.PaymentFailedEvent
import com.food.ordering.system.payment.service.domain.ports.output.message.publisher.PaymentFailedMessagePublisher
import com.food.ordering.system.payment.service.messaging.mapper.PaymentMessagingDataMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class PaymentFailedKafkaMessagePublisher(
    private val paymentMessagingDataMapper: PaymentMessagingDataMapper,
    private val paymentKafkaProducer: KafkaProducer<String, PaymentResponseAvroModel>,
    private val paymentServiceConfigData: PaymentServiceConfigData,
    private val kafkaMessageHelper: KafkaMessageHelper
): PaymentFailedMessagePublisher {
    private val logger = LoggerFactory.getLogger(PaymentFailedKafkaMessagePublisher::class.java)

    override fun publish(domainEvent: PaymentFailedEvent) {
        val orderId = domainEvent.payment.orderId.value.toString()
        logger.info("Publishing payment completed event for order id: $orderId")
        val paymentResponseAvroModel =
            paymentMessagingDataMapper.paymentFailedEventToPaymentResponseAvroModel(domainEvent)
        try {
            paymentKafkaProducer.send(
                paymentServiceConfigData.paymentResponseTopicName!!,
                orderId,
                paymentResponseAvroModel,
                kafkaMessageHelper.getKafkaCallback(
                    paymentServiceConfigData.paymentRequestTopicName!!,
                    paymentResponseAvroModel,
                    orderId
                )
            )

            logger.info("Payment completed event for order id: $orderId")
        } catch (e: Exception) {
            logger.error("Error while publishing payment completed event for order id: $orderId, error: ${e.message}")
        }
    }
}