repositories {
    mavenCentral()
    maven {
        url = uri("https://packages.confluent.io/maven/")
    }
}

dependencyManagement {
    dependencies {
        dependency("org.springframwork.kafka:spring-kafka:3.3.10")
        dependency("io.confluent:kafka-avro-serializer:8.1.0") {
            exclude("org.slf4j:slf4j-log4j12")
            exclude("log4j:log4j")
            exclude("io.swagger:swagger-annotations")
            exclude("io.swagger:swagger-core")
        }
        dependency("org.apache.avro:avro:1.12.1")
    }
}
