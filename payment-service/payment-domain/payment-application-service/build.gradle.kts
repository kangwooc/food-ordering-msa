dependencies {
    api(project(":payment-service:payment-domain:payment-domain-core"))
    api(project(":common:common-domain"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework:spring-tx")
    implementation("org.springframework.boot:spring-boot-starter-validation")
}