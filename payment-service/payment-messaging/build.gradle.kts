dependencies {
    implementation(project(":payment-service:payment-domain:payment-application-service"))
    implementation(project(":infrastructure:kafka:kafka-producer"))
    implementation(project(":infrastructure:kafka:kafka-consumer"))
    implementation(project(":infrastructure:kafka:kafka-model"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}