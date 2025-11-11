dependencies {
    implementation(project(":restaurant-service:restaurant-domain:restaurant-application-service"))
    implementation(project(":infrastructure:kafka:kafka-producer"))
    implementation(project(":infrastructure:kafka:kafka-consumer"))
    implementation(project(":infrastructure:kafka:kafka-model"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}