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
    "common:common-domain"
)
