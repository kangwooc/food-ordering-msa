package com.food.ordering.system.kafka.consumer.config

import kafka.config.data.KafkaConfigData
import kafka.config.data.KafkaConsumerConfigData
import org.apache.avro.specific.SpecificRecordBase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.Serializable

@Configuration
class KafkaConsumerConfig<K: Serializable, V: SpecificRecordBase>(
    private val kafkaConsumerConfigData: KafkaConsumerConfigData,
    private val kafkaConfigData: KafkaConfigData
) {
    @Bean
    fun
}