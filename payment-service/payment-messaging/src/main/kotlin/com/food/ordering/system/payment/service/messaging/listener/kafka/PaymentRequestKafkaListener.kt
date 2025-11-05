package com.food.ordering.system.payment.service.messaging.listener.kafka

import com.food.ordering.system.kafka.consumer.KafkaConsumer
import com.food.ordering.system.kafka.order.avro.model.PaymentOrderStatus
import com.food.ordering.system.kafka.order.avro.model.PaymentRequestAvroModel
import com.food.ordering.system.payment.service.domain.ports.input.message.listener.PaymentRequestMessageListener
import com.food.ordering.system.payment.service.messaging.mapper.PaymentMessagingDataMapper
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class PaymentRequestKafkaListener(
    private val paymentRequestMessageListener: PaymentRequestMessageListener,
    private val paymentMessagingDataMapper: PaymentMessagingDataMapper,
): KafkaConsumer<PaymentRequestAvroModel> {
    private val logger = LoggerFactory.getLogger(PaymentRequestKafkaListener::class.java)

    @KafkaListener(
        topics = ["\${payment-service.payment-request-topic-name}"],
        groupId = "\${kafka-consumer-config.payment-consumer-group-id}"
    )
    override fun receive(
        @Payload message: List<PaymentRequestAvroModel>,
        @Header(KafkaHeaders.RECEIVED_KEY) key: List<String>,
        @Header(KafkaHeaders.RECEIVED_PARTITION) partition: List<Int>,
        @Header(KafkaHeaders.OFFSET) offsets: List<Long>
    ) {
        logger.info(
            "Received ${message.size} payment response messages with keys: $key, partitions: $partition, offsets: $offsets"
        )

        message.forEach { it ->
            if (it.paymentOrderStatus == PaymentOrderStatus.PENDING) {
                logger.info("Processing payment request for order id: ${it.orderId}")
                paymentRequestMessageListener.completePayment(
                    paymentMessagingDataMapper.paymentRequestAvroModelToPaymentRequest(it)
                )
            } else if (it.paymentOrderStatus == PaymentOrderStatus.CANCELLED) {
                logger.info("Processing payment cancellation for order id: ${it.orderId}")
                paymentRequestMessageListener.cancelPayment(
                    paymentMessagingDataMapper.paymentRequestAvroModelToPaymentRequest(it)
                )
            }
        }
    }
}