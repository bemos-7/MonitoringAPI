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
        implementation("com.github.oshi:oshi-core:6.6.5")

        implementation("ch.qos.logback:logback-classic:1.5.15")
    }

    tasks.test {
        useJUnitPlatform()
    }

    tasks.register<Jar>("fatJar") {
        archiveBaseName.set("MyApp")
        archiveVersion.set("1.0")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        manifest {
            attributes["Main-Class"] = "MainKt" // Замените на ваш класс с main
        }
        from(sourceSets.main.get().output)
        dependsOn(configurations.runtimeClasspath)
        from({
            configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }
        })
    }

    kotlin {
        jvmToolchain(8)
    }

    application {
        mainClass.set("MainKt")
    }