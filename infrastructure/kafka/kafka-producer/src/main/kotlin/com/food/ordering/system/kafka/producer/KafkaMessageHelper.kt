package com.food.ordering.system.kafka.producer

import org.slf4j.LoggerFactory
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component

@Component
class KafkaMessageHelper {
    private val logger = LoggerFactory.getLogger(KafkaMessageHelper::class.java)

    fun<T> getKafkaCallback(
        topicName: String,
        model: T,
        orderId: String,
    ): (result: SendResult<*,*>?, ex: Throwable?) -> Unit = { result, ex ->
        if (ex != null) {
            logger.error(ex.message, ex)
            throw ex
        }

        val metaData = result!!.recordMetadata
        val modelName = model!!::class.simpleName
        logger.info(
            "$modelName Received sent to Kafka for order id: ${orderId} to topic: ${metaData.topic()} " +
                    "partition: ${metaData.partition()} at offset: ${metaData.offset()}"
        )
    }
}