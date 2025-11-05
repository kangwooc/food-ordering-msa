dependencies {
    implementation(project(":common:common-domain"))

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("org.springframework.boot:spring-boot-starter-logging")
    runtimeOnly("io.github.oshai:kotlin-logging-jvm:7.0.13")
}