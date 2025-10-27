plugins {
    kotlin("plugin.serialization")
    kotlin("kapt")

    id("com.github.davidmc24.gradle.plugin.avro")
}

dependencies {
    implementation("com.github.avro-kotlin.avro4k:avro4k-core:2.6.0")
    // kafka
    implementation("org.springframework.kafka:spring-kafka")

    // avro
    kapt("org.apache.avro:avro:1.12.1")
    implementation("org.apache.avro:avro:1.12.1")
    implementation("tools.jackson.dataformat:jackson-dataformat-avro:3.0.1")

    kapt("io.confluent:kafka-avro-serializer:8.1.0")
    implementation("io.confluent:kafka-avro-serializer:8.1.0")
}

avro {
    stringType.set("String")
    setEnableDecimalLogicalType(true)
}

sourceSets {
    main {
        java {
            srcDirs("build/generated-main-avro-java")
        }
    }
}