    plugins {
        kotlin("jvm") version "2.1.0"
        id("io.ktor.plugin") version "2.3.4"
        id("org.jetbrains.kotlin.plugin.serialization") version "2.1.0"
        application
    }

    group = "org.bemos"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
    val exposedVersion: String by project
    val ktorVersion: String by project
    val sqLite: String by project
    dependencies {

        testImplementation(kotlin("test"))
        implementation("io.ktor:ktor-server-core:$ktorVersion")
        implementation("io.ktor:ktor-server-netty:$ktorVersion")
        implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
        implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
        testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
        testImplementation("org.jetbrains.kotlin:kotlin-test:2.1.0")
        implementation("io.ktor:ktor-server-call-logging:$ktorVersion")
        implementation("com.github.oshi:oshi-core:6.6.5")
        implementation("ch.qos.logback:logback-classic:1.5.15")
        implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
        implementation("org.jetbrains.exposed:exposed-crypt:$exposedVersion")
        implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
        implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
        implementation("org.xerial:sqlite-jdbc:$sqLite")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    }

    tasks.test {
        useJUnitPlatform()
    }

    tasks.register<Jar>("fatJar") {
        archiveBaseName.set("MonitoringService")
        archiveVersion.set("1.0")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        manifest {
            attributes["Main-Class"] = "MainKt"
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

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    application {
        mainClass.set("MainKt")
    }