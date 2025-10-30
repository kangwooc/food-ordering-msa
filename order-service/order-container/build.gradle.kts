plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("kapt")

    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation(project(":order-service:order-domain:order-domain-core"))
    implementation(project(":common:common-domain"))
    implementation(project(":order-service:order-application"))
    implementation(project(":order-service:order-dataaccess"))
    implementation(project(":order-service:order-messaging"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    kapt("org.springframework.boot:spring-boot-configuration-processor")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
