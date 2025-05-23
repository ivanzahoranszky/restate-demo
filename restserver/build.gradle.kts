plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("io.ktor.plugin")
    id("com.google.cloud.tools.jib")
    application
}

val restateVersion: String by rootProject
val coroutineVersion: String by rootProject
val serializationVersion: String by rootProject
val configVersion: String by rootProject
val logbackVersion: String by rootProject
val ktorVersion: String by rootProject
val koinVersion: String by rootProject

kotlin {
    jvmToolchain(21)
}

dependencies {
    // Project dependencies
    implementation(project(":common"))
    implementation(project(":transaction"))
    implementation(project(":stats"))
    
    // Kotlin
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")
    
    // Restate Client
    implementation("dev.restate:client-kotlin:$restateVersion")
    
    // Config
    implementation("com.typesafe:config:$configVersion")
    
    // Logging
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    
    // Ktor
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-core:${ktorVersion}")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:${ktorVersion}")
    implementation("io.ktor:ktor-client-content-negotiation:${ktorVersion}")
    implementation("io.ktor:ktor-server-double-receive:${ktorVersion}")
    
    // Koin for dependency injection
    implementation("io.insert-koin:koin-core:$koinVersion")

    testImplementation("dev.restate:sdk-kotlin-http:${restateVersion}")

    testImplementation("io.cucumber:cucumber-java:7.14.0")
    testImplementation("io.cucumber:cucumber-junit:7.14.0")
    testImplementation("junit:junit:4.13.2")

    testImplementation("org.testcontainers:testcontainers:1.19.8")
    testImplementation("org.testcontainers:postgresql:1.19.3")

}

application {
    mainClass.set("ivan.restserver.AppMainKt")
}

jib {
    from {
        image = "eclipse-temurin:21-jdk-alpine"
    }
    to {
        image = "ivan/rest-server"
    }
    container {
        mainClass = application.mainClass.get()
        ports = listOf("9000")
        environment = mapOf(
            "PORT" to "9000"
        )
    }
}

tasks.withType<Test> {
    dependsOn("jibDockerBuild")
    systemProperty("cucumber.options", "--plugin pretty")
    include("**/RunCucumberTest.class")
    testLogging {
        events("passed", "skipped", "failed")
    }
}
