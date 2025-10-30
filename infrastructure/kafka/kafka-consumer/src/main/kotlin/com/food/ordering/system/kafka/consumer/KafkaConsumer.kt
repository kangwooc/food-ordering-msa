package com.food.ordering.system.kafka.consumer

interface KafkaConsumer<T> {
    fun receive(message: List<T>, key: List<String>, partition: List<Int>, offsets: List<Long>)
}