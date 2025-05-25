plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version("0.8.0")
//  id("com.github.johnrengelman.shadow") version "7.0.0" apply false
}

rootProject.name = "RestateDemo"

include("common")
include("transaction")
include("stats")
include("payment")
include("account")
include("restserver")