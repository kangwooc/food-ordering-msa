package com.food.ordering.system.kafka.consumer.config

import com.food.ordering.system.kafka.config.data.KafkaConfigData
import com.food.ordering.system.kafka.config.data.KafkaConsumerConfigData
import org.apache.avro.specific.SpecificRecordBase
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer
import java.io.Serializable

@Configuration
class KafkaConsumerConfig<K: Serializable, V: SpecificRecordBase>(
    private val kafkaConsumerConfigData: KafkaConsumerConfigData,
    private val kafkaConfigData: KafkaConfigData
) {
    @Bean
    fun consumerConfig(): Map<String, Any?> {
        return mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaConfigData.bootstrapServers,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to kafkaConsumerConfigData.keyDeserializer,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to kafkaConsumerConfigData.valueDeserializer,
            kafkaConfigData.schemaRegistryUrlKey!! to kafkaConfigData.schemaRegistryUrl,
            kafkaConsumerConfigData.specificAvroReaderKey!! to kafkaConsumerConfigData.specificAvroReader,
            ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG to kafkaConsumerConfigData.sessionTimeoutMs,
            ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG to kafkaConsumerConfigData.heartbeatIntervalMs,
            ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG to kafkaConsumerConfigData.maxPollIntervalMs,
            ConsumerConfig.MAX_POLL_RECORDS_CONFIG to kafkaConsumerConfigData.maxPollRecords,
            ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG to kafkaConsumerConfigData.maxPartitionFetchBytesDefault!! * kafkaConsumerConfigData.maxPartitionFetchBytesBoostFactor!!,
        )
    }

    @Bean
    fun consumerFactory(): ConsumerFactory<K, V> {
        return DefaultKafkaConsumerFactory(consumerConfig())
    }

    @Bean
    fun kafkaListenerContainerFactory(): KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<K, V>> {
        val factory = ConcurrentKafkaListenerContainerFactory<K, V>()
        factory.consumerFactory = consumerFactory()
        factory.isBatchListener = kafkaConsumerConfigData.batchListener!!
        factory.setConcurrency(kafkaConsumerConfigData.concurrencyLevel!!)
        factory.setAutoStartup(kafkaConsumerConfigData.autoStartup!!)
        factory.containerProperties.pollTimeout = kafkaConsumerConfigData.pollTimeoutMs!!.toLong()
        return factory
    }
}