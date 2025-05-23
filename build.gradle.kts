plugins {
  kotlin("jvm") version "2.0.0" apply false
  kotlin("plugin.serialization") version "2.0.0" apply false
  id("com.google.devtools.ksp") version "2.0.0-1.0.21" apply false
  id("io.ktor.plugin") version "3.1.3" apply false
  id("com.google.cloud.tools.jib") version "3.3.0" apply false
}

extra.apply { set("restateVersion","2.1.0")
  set("coroutineVersion","1.10.2")
  set("serializationVersion","1.8.1")
  set("exposedVersion","0.61.0")
  set("postgresVersion","42.7.2")
  set("hikariVersion","5.0.1")
  set("configVersion","1.4.3")
  set("logbackVersion","1.5.13")
  set("ktorVersion","3.1.3")
  set("koinVersion","3.5.3")
}

allprojects {
  val restateVersion: String by rootProject.extra
  val coroutineVersion: String by rootProject.extra
  val serializationVersion: String by rootProject.extra
  val exposedVersion: String by rootProject.extra
  val postgresVersion: String by rootProject.extra
  val hikariVersion: String by rootProject.extra
  val configVersion: String by rootProject.extra
  val logbackVersion: String by rootProject.extra
  val ktorVersion: String by rootProject.extra
  val koinVersion: String by rootProject.extra

  repositories {
    mavenCentral()
  }
}

subprojects {
  group = "ivan"
  version = "1.0.0"
}


