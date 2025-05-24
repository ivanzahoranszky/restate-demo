package stepdefs

import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName

class StatsTestContainer(imageName: String = "ivan/stats-service") :
    GenericContainer<StatsTestContainer>(DockerImageName.parse(imageName)) {

    init {
        withExposedPorts(9080)
    }

}