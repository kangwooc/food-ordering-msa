import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    val kotlinVersion = "2.2.21"
    val springBootVersion = "3.5.7"
    val springDependencyManagementVersion = "1.1.7"
    val avroVersion = "1.9.1"
    val avroKotlinVersion = "2.6.0"

    kotlin("jvm") version kotlinVersion apply false
    kotlin("plugin.spring") version kotlinVersion apply false
    kotlin("plugin.jpa") version kotlinVersion apply false
    kotlin("kapt") version kotlinVersion apply false
    kotlin("plugin.serialization") version kotlinVersion apply false

    id("org.springframework.boot") version springBootVersion apply false
    id("io.spring.dependency-management") version springDependencyManagementVersion apply false
    id("com.github.davidmc24.gradle.plugin.avro") version avroVersion apply false
    id("io.github.avro-kotlin") version avroKotlinVersion apply false
}

allprojects {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://packages.confluent.io/maven/")
    }

    tasks.withType<KotlinJvmCompile>().configureEach {
        compilerOptions {
            freeCompilerArgs.addAll("-Xjsr305=strict")
        }
    }

    configurations.all {
        resolutionStrategy {
            force("org.jetbrains.kotlinx:kotlinx-serialization-core:1.9.0")
            force("org.jetbrains.kotlinx:kotlinx-serialization-core-jvm:1.9.0")
            force("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
            force("org.jetbrains.kotlinx:kotlinx-serialization-json-jvm:1.9.0")
            force("org.jetbrains.kotlinx:kotlinx-serialization-bom:1.9.0")
        }
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    the<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension>().apply {
        imports {
            mavenBom("org.jetbrains.kotlinx:kotlinx-serialization-bom:1.9.0")
        }
    }

    plugins.withId("org.jetbrains.kotlin.jvm") {
        // kotlin 확장을 타입으로 꺼내서 설정
        extensions.configure<KotlinJvmProjectExtension>("kotlin") {
            jvmToolchain(21)
        }
    }

    tasks.named<KotlinJvmCompile>("compileKotlin"){
        compilerOptions {
            apiVersion.set(KotlinVersion.KOTLIN_2_2)
        }
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()

        maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).takeIf { it > 0 } ?: 1
    }

    // 라이브러리 모듈이므로 bootJar 비활성화
    tasks.named<BootJar>("bootJar") {
        enabled = false
    }

    tasks.named<Jar>("jar") {
        enabled = true
    }
}
