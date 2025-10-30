package com.food.ordering.system.order.service.messaging.listener.kafka

import com.food.ordering.orderapplicationservice.dto.message.PaymentResponse
import com.food.ordering.orderapplicationservice.ports.input.message.listener.payment.PaymentResponseMessageListener
import com.food.ordering.system.kafka.consumer.KafkaConsumer
import com.food.ordering.system.order.service.messaging.mapper.OrderMessagingDataMapper
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class PaymentResponseKafkaListener(
    private val paymentResponseMessageListener: PaymentResponseMessageListener,
    private val orderMessagingDataMapper: OrderMessagingDataMapper
): KafkaConsumer<PaymentResponse> {
    private val logger = LoggerFactory.getLogger(PaymentResponseKafkaListener::class.java)

    @KafkaListener(id = "\${kafka-consumer-config.payment-consumer-group-id}", topics = ["\${order-service.payment-response-topic-name}"])
    override fun receive(message: List<PaymentResponse>, key: List<Long>, partition: List<Int>, offsets: List<Long>) {

    }
}