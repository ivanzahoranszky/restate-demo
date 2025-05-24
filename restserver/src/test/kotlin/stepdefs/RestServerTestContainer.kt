package stepdefs

import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName

class RestServerTestContainer(imageName: String = "ivan/rest-server") :
    GenericContainer<RestServerTestContainer>(DockerImageName.parse(imageName)) {

    init {
        withExposedPorts(9000)
    }

}