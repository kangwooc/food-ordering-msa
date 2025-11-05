dependencies {
    implementation(project(":payment-service:payment-domain:payment-application-service"))
    implementation(project(":payment-service:payment-domain:payment-domain-core"))
    implementation(project(":payment-service:payment-dataaccess"))
    implementation(project(":payment-service:payment-messaging"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}