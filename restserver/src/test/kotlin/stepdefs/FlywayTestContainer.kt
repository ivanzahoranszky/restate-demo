package stepdefs

import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName

class FlywayTestContainer(imageName: String = "flyway/flyway:10-alpine") :
    GenericContainer<FlywayTestContainer>(DockerImageName.parse(imageName))