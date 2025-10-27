package com.food.ordering.system.kafka.consumer

import org.apache.avro.specific.SpecificRecordBase

interface KafkaConsumer<T: SpecificRecordBase> {
    fun receive(message: List<T>, key: List<Long>, partition: List<Int>, offsets: List<Long>)
}