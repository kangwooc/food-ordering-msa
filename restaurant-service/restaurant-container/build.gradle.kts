dependencies {
    implementation(project(":common:common-dataaccess"))
    implementation(project(":restaurant-service:restaurant-domain:restaurant-application-service"))
    implementation(project(":restaurant-service:restaurant-dataaccess"))
    implementation(project(":restaurant-service:restaurant-messaging"))

    implementation("org.springframework.boot:spring-boot-starter")
}

