dependencies {
    api(project(":common:common-domain"))

    implementation("org.springframework.boot:spring-boot-starter-logging")
    runtimeOnly("io.github.oshai:kotlin-logging-jvm:7.0.13")
}