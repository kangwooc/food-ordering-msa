package kafka.config.data

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "kafka-config")
data class KafkaConfigData(
    val bootstrapServers: String? = null,
    val schemaRegistryUrlKey: String? = null,
    val schemaRegistryUrl: String? = null,
    val numOfPartitions: Int? = null,
    val replicationFactor: Short? = null,
)