package com.food.ordering.system.kafka.producer.service.impl

import com.food.ordering.system.kafka.producer.exception.KafkaProducerException
import com.food.ordering.system.kafka.producer.service.KafkaProducer
import jakarta.annotation.PreDestroy
import org.slf4j.LoggerFactory
import org.springframework.kafka.KafkaException
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import java.io.Serializable

@Component
class KafkaProducerImpl<K: Serializable, V>(
    private val kafkaTemplate: KafkaTemplate<K, V>
): KafkaProducer<K, V> {
    private val logger = LoggerFactory.getLogger(KafkaProducerImpl::class.java)
    override fun send(
        topicName: String,
        key: K,
        value: V,
        callback: (result: SendResult<K, V>?, ex: Throwable?) -> Unit
    ) {
        logger.info("Message sent=$value to kafka topic=$topicName")
        try {
            val result = kafkaTemplate.send(topicName, key, value)
            result.whenComplete(callback)
        } catch (ex: KafkaException) {
            logger.error("Error while sending message=$value to kafka topic=$topicName, error=${ex.message}")
            throw KafkaProducerException("Error while sending message=$value to kafka topic=$topicName")
        }
    }

    @PreDestroy
    fun close() {
        try {
            logger.info("Closing kafka producer")
            kafkaTemplate.destroy()
        } catch (ex: Exception) {
            logger.error("Error while closing kafka producer: ${ex.message}")
        }
    }
}