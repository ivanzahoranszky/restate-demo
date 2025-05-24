package stepdefs

import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName

class TransactionTestContainer(imageName: String = "ivan/transaction-service") :
    GenericContainer<TransactionTestContainer>(DockerImageName.parse(imageName)) {

    init {
        withExposedPorts(9080)
    }

}