plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

kotlin {
    jvmToolchain(21)
}

val serializationVersion: String by rootProject

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:${serializationVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${serializationVersion}")
    implementation("com.ionspin.kotlin:bignum:0.3.10")
}