package com.food.ordering.system.order.service.messaging.listener.kafka

import com.food.ordering.orderapplicationservice.ports.input.message.listener.payment.PaymentResponseMessageListener
import com.food.ordering.system.kafka.consumer.KafkaConsumer
import com.food.ordering.system.kafka.order.avro.model.PaymentResponseAvroModel
import com.food.ordering.system.kafka.order.avro.model.PaymentStatus
import com.food.ordering.system.order.service.messaging.mapper.OrderMessagingDataMapper
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class PaymentResponseKafkaListener(
    private val paymentResponseMessageListener: PaymentResponseMessageListener,
    private val orderMessagingDataMapper: OrderMessagingDataMapper
) : KafkaConsumer<PaymentResponseAvroModel> {
    private val logger = LoggerFactory.getLogger(PaymentResponseKafkaListener::class.java)

    @KafkaListener(
        id = "\${kafka-consumer-config.payment-consumer-group-id}",
        topics = ["\${order-service.payment-response-topic-name}"]
    )
    override fun receive(
        @Payload message: List<PaymentResponseAvroModel>,
        @Header(KafkaHeaders.RECEIVED_KEY) key: List<String>,
        @Header(KafkaHeaders.RECEIVED_PARTITION) partition: List<Int>,
        @Header(KafkaHeaders.OFFSET) offsets: List<Long>
    ) {
        logger.info("Received ${message.size} payment responses, keys: $key, partitions: $partition, offsets: $offsets")
        message.forEach { paymentResponseAvroModel ->
            if (paymentResponseAvroModel.paymentStatus == PaymentStatus.COMPLETED) {
                logger.info("Processing payment for order id: ${paymentResponseAvroModel.orderId} with status: ${paymentResponseAvroModel.paymentStatus}")
                paymentResponseMessageListener.paymentCompleted(
                    orderMessagingDataMapper.paymentResponseAvroModelToPaymentResponse(paymentResponseAvroModel)
                )
            } else if (paymentResponseAvroModel.paymentStatus == PaymentStatus.CANCELLED || paymentResponseAvroModel.paymentStatus == PaymentStatus.FAILED) {
                logger.info("Processing payment cancellation for order id: ${paymentResponseAvroModel.orderId} with status: ${paymentResponseAvroModel.paymentStatus}")
                paymentResponseMessageListener.paymentCancelled(
                    orderMessagingDataMapper.paymentResponseAvroModelToPaymentResponse(paymentResponseAvroModel)
                )
            }
        }
    }
}