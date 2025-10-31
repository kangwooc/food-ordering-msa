plugins {
    kotlin("plugin.serialization")
    kotlin("kapt")

    id("com.github.davidmc24.gradle.plugin.avro")
    id("io.github.avro-kotlin")
}

dependencies {
    // kafka
    api("org.springframework.kafka:spring-kafka")

    // avro
    implementation("tools.jackson.dataformat:jackson-dataformat-avro:3.0.1")

    implementation("com.github.avro-kotlin.avro4k:avro4k-core:2.6.0")
    implementation("com.github.avro-kotlin.avro4k:avro4k-kotlin-generator:2.6.0")
    api("com.github.avro-kotlin.avro4k:avro4k-confluent-kafka-serializer:2.6.0")

    runtimeOnly("org.jetbrains.kotlinx:kotlinx-serialization-core:1.9.0")
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
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