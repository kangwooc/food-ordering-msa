pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "food-ordering-system"

include(
    "order-service",
    "order-service:order-application",
    "order-service:order-container",
    "order-service:order-domain",
    "order-service:order-dataaccess",
    "order-service:order-messaging",
    "order-service:order-domain:order-application-service",
    "order-service:order-domain:order-domain-core",
    "common:common-domain",
    "infrastructure",
)

include("infrastructure:kafka")
include("infrastructure:kafka:kafka-consumer")
include("infrastructure:kafka:kafka-producer")
include("infrastructure:kafka:kafka-model")
include("infrastructure:kafka:kafka-config-data")
include("common:common-application")