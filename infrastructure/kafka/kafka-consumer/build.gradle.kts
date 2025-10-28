dependencies {
    implementation(project(":infrastructure:kafka:kafka-model"))
    implementation(project(":infrastructure:kafka:kafka-config-data"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // kafka
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.apache.avro:avro:1.12.1")
    implementation("io.confluent:kafka-avro-serializer:8.1.0")
}