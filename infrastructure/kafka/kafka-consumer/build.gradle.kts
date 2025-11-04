plugins {
    kotlin("plugin.spring")
    kotlin("plugin.serialization")
}

dependencies {
    implementation(project(":infrastructure:kafka:kafka-model"))
    implementation(project(":infrastructure:kafka:kafka-config-data"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core-jvm:1.7.3")
}