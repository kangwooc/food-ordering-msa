plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.serialization")
}

dependencies {
    implementation(project(":infrastructure:kafka:kafka-model"))
    implementation(project(":infrastructure:kafka:kafka-config-data"))
    implementation(project(":order-service:order-domain:order-domain-core"))
    implementation(project(":common:common-domain"))

    implementation("org.springframework.boot:spring-boot-starter")
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-serialization-core:1.7.3")
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-serialization-core-jvm:1.7.3")
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-serialization-json-jvm:1.7.3")
}