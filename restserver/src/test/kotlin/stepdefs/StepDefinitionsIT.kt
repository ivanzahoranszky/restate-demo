package stepdefs

import io.cucumber.datatable.DataTable
import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.en.Given
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.testcontainers.containers.PostgreSQLContainer
import java.math.BigDecimal

class StepDefinitionsIT {

    // Pair(expected, actual)
    private var results = setOf<Pair<String, String>>()

    private val httpClient = HttpClient()

    private lateinit var restateTestContainer: RestateTestContainer
    private var postgresContainer = PostgreSQLContainer("postgres:13")
    private lateinit var restServerTestContainer: RestServerTestContainer

    @Before
    fun setup() {
        postgresContainer.apply {
            withUsername("ivan")
            withPassword("ivan")
            withDatabaseName("postgres")
            start()
        }

        val flywayTestContainer = FlywayTestContainer().apply {
            withFileSystemBind("../flyway/sql", "/flyway/db/migrations")
            withEnv("FLYWAY_URL", "jdbc:postgresql://host.docker.internal:${postgresContainer.getMappedPort(5432)}/postgres")
            withEnv("FLYWAY_USER", "ivan")
            withEnv("FLYWAY_PASSWORD", "ivan")
            withEnv("FLYWAY_LOCATIONS", "filesystem:/flyway/db/migrations")
            withEnv("FLYWAY_BASELINE_ON_MIGRATE", "true")
            withCommand("migrate")
            start()
        }

        restateTestContainer = RestateTestContainer().apply { start() }

        val transactionTestContainer = TransactionTestContainer().apply {
            withEnv("PORT", "9080")
            withEnv("POSTGRES_URL", "jdbc:postgresql://host.docker.internal:${postgresContainer.getMappedPort(5432)}/postgres")
            withEnv("POSTGRES_USER", "ivan")
            withEnv("POSTGRES_PASSWORD", "ivan")
            withEnv("POSTGRES_DB", "postgres")
            start()
        }

        val statsTestContainertat = StatsTestContainer().apply {
            withEnv("PORT", "9080")
            start()
        }

        restServerTestContainer = RestServerTestContainer().apply {
            withEnv("RESTSERVER_RESTATE_URL", "http://host.docker.internal:${restateTestContainer.getMappedPort(8080)}/TransactionService/fee")
            start()
        }

        // register the services first
        val port = restateTestContainer.getMappedPort(RestateTestContainer.DEPLOY_PORT)
        runBlocking {
            httpClient.post("http://localhost:$port/deployments") {
                contentType(ContentType.Application.Json)
                setBody("""{"uri": "http://host.docker.internal:${transactionTestContainer.getMappedPort(9080)}"}""")
            }
            httpClient.post("http://localhost:$port/deployments") {
                contentType(ContentType.Application.Json)
                setBody("""{"uri": "http://host.docker.internal:${statsTestContainertat.getMappedPort(9080)}"}""")
            }
        }
    }

    @After
    fun tearDown() {
        restateTestContainer.stop()
        postgresContainer.stop()
    }

    @Given("the system is up and running")
    fun `the system is up and running`() {
        // do nothing
    }

    @Given("^the system is called with the following requests:$")
    fun `the system is called with the following requests`(dataTable: DataTable) {
        runBlocking {
            val targetUrl = "http://localhost:${restServerTestContainer.getMappedPort(9000)}/transaction/fee"
            dataTable.asMaps(String::class.java, String::class.java).map { row ->
                val expected = row["expected_response"] ?: ""
                val body = """{ "transactionId": "${row["transaction_id"] ?: ""}", "amount": "${BigDecimal(row["amount"])}", "assetType": "${row["asset_type"]}", "asset": "${row["asset"]}" }""".trimMargin()
                val response = httpClient.post(targetUrl) {
                    contentType(ContentType.Application.Json)
                    setBody(body)
                }
                results += expected to response.bodyAsText()
            }
        }
    }


    @Given("^it returns the correct responses$")
    fun `it returns the correct responses`() {
        results.forEach {
            assertEquals(it.first, it.second)
        }
    }

}