    plugins {
        kotlin("jvm") version "1.9.24"
        id("io.ktor.plugin") version "2.3.4"
        id("org.jetbrains.kotlin.plugin.serialization") version "1.8.0"
        application
    }

    group = "org.bemos"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    dependencies {
        val ktorVersion = "2.3.4"
        testImplementation(kotlin("test"))
        implementation("io.ktor:ktor-server-core:$ktorVersion")
        implementation("io.ktor:ktor-server-netty:$ktorVersion")
        implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
        implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
        testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
        testImplementation("org.jetbrains.kotlin:kotlin-test:1.9.10")
        implementation("io.ktor:ktor-server-call-logging:$ktorVersion")

        implementation("ch.qos.logback:logback-classic:1.5.15")
    }

    tasks.test {
        useJUnitPlatform()
    }

    kotlin {
        jvmToolchain(8)
    }

    application {
        mainClass.set("MainKt")
    }