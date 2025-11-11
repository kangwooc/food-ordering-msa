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
include("customer-service")
include("payment-service")
include("payment-service:payment-dataaccess")
include("payment-service:payment-domain")
include("payment-service:payment-messaging")
include("payment-service:payment-container")
include("payment-service:payment-domain:payment-domain-core")
include("payment-service:payment-domain:payment-application-service")
include("restaurant-service")
include("restaurant-service:restaurant-domain")
include("restaurant-service:restaurant-container")
include("restaurant-service:restaurant-dataaccess")
include("restaurant-service:restaurant-messaging")
include("restaurant-service:restaurant-domain:restaurant-application-service")
include("restaurant-service:restaurant-domain:restaurant-domain-core")
include("common:common-dataaccess")
include("infrastructure:saga")