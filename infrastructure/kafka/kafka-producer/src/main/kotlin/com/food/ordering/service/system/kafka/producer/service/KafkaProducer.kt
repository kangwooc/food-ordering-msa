package com.food.ordering.service.system.kafka.producer.service

import org.apache.avro.specific.SpecificRecordBase
import org.springframework.kafka.support.SendResult
import java.io.Serializable

interface KafkaProducer<K: Serializable, V: SpecificRecordBase> {
    fun send(topicName: String, key: K, value: V, callback: (result: SendResult<K, V>?, ex: Throwable?) -> Unit)
}