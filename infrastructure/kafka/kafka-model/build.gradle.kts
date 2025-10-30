plugins {
    kotlin("plugin.serialization")
    kotlin("kapt")

    id("com.github.davidmc24.gradle.plugin.avro")
    id("io.github.avro-kotlin")
}

dependencies {
    implementation("com.github.avro-kotlin.avro4k:avro4k-core:2.6.0")
    implementation("com.github.avro-kotlin.avro4k:avro4k-kotlin-generator:2.6.0")
    // kafka
    api("org.springframework.kafka:spring-kafka")

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

avro4k {
    sourcesGeneration {
        inputSchemas.from(sourceSets.main.get().allSource)
        outputDir.set(file("src/main/kotlin"))
    }
}

sourceSets {
    main {
        resources {
            srcDirs("src/main/resources/avro")
        }
    }
}