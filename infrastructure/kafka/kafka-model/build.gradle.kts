plugins {
    kotlin("plugin.serialization")
    kotlin("kapt")

    // Pin plugin versions for stable behavior
    id("com.github.davidmc24.gradle.plugin.avro")
    id("io.github.avro-kotlin")
}

dependencies {
    // kafka
    api("org.springframework.kafka:spring-kafka")

    // avro
    implementation("com.github.avro-kotlin.avro4k:avro4k-core")
    implementation("com.github.avro-kotlin.avro4k:avro4k-kotlin-generator")
    api("com.github.avro-kotlin.avro4k:avro4k-confluent-kafka-serializer:2.6.0")

    runtimeOnly("org.jetbrains.kotlinx:kotlinx-serialization-core:1.7.3")
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-serialization-json-jvm:1.7.3")
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-serialization-core-jvm:1.7.3")
}

avro {
    stringType.set("String")
    setEnableDecimalLogicalType(true)
}

avro4k {
    sourcesGeneration {
        // Use explicit avro resources folder to avoid early-evaluation issues
        inputSchemas.from(file("src/main/resources/avro"))
        // Put generated sources directly into the project's source directory as requested,
        // but use the lazy project layout provider to avoid MissingValueException.
        outputDir.set(layout.projectDirectory.dir("src/main/kotlin"))
    }
}

sourceSets {
    main {
        kotlin {
            srcDirs("src/main/kotlin")
        }
        resources {
            srcDirs("src/main/resources/avro")
        }
    }
}