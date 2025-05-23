package stepdefs

import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName

class RestateTestContainer(imageName: String = "docker.restate.dev/restatedev/restate:1.3") :
    GenericContainer<RestateTestContainer>(DockerImageName.parse(imageName)) {

    companion object {
        const val APP_PORT = 8080
        const val DEPLOY_PORT = 9070
        const val METRICS_PORT = 9071
    }

    init {
        withExposedPorts(APP_PORT, DEPLOY_PORT, METRICS_PORT)
    }

}