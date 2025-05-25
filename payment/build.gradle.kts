import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("com.google.devtools.ksp")
    id("com.google.cloud.tools.jib")
    application
}

val restateVersion: String by rootProject
val coroutineVersion: String by rootProject
val serializationVersion: String by rootProject
val exposedVersion: String by rootProject
val postgresVersion: String by rootProject
val hikariVersion: String by rootProject
val configVersion: String by rootProject
val logbackVersion: String by rootProject
val koinVersion: String by rootProject

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(project(":common"))
    
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")

    ksp("dev.restate:sdk-api-kotlin-gen:$restateVersion")
    implementation("dev.restate:sdk-kotlin-http:$restateVersion")

    implementation("io.insert-koin:koin-core:${koinVersion}")

    implementation("com.typesafe:config:$configVersion")

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.postgresql:postgresql:$postgresVersion")
    implementation("com.zaxxer:HikariCP:$hikariVersion")

    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    testImplementation("io.cucumber:cucumber-java:7.14.0")
    testImplementation("io.cucumber:cucumber-junit:7.14.0")
    testImplementation("junit:junit:4.13.2")

    // Only for Mac OS
    runtimeOnly("io.netty:netty-resolver-dns-native-macos:4.2.1.Final:osx-x86_64")
}

application {
    mainClass.set("ivan.stats.AppMain")
}

jib {
    from {
        image = "eclipse-temurin:21-jdk-alpine"
    }
    to {
        image = "ivan/stats-service"
    }
    container {
        mainClass = application.mainClass.get()
        args = listOf("--enable-preview", "--add-modules=ALL-UNNAMED")
        ports = listOf("9080")
        environment = mapOf(
            "PORT" to "8080"
        )
    }
}

tasks.withType<Test> {
    systemProperty("cucumber.options", "--plugin pretty")
    include("**/RunCucumberTest.class")
    testLogging {
        events("passed", "skipped", "failed")
    }
}
