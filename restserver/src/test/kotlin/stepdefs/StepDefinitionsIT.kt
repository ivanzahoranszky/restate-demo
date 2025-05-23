package stepdefs

import dev.restate.sdk.endpoint.Endpoint
import dev.restate.sdk.http.vertx.RestateHttpServer
import io.cucumber.datatable.DataTable
import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.en.Given
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ivan.restserver.restServerDependenciesModule
import ivan.restserver.restServerModule
import ivan.stats.service.StatsService
import ivan.stats.statsServiceModule
import ivan.transaction.service.TransactionService
import ivan.transaction.transactionServiceModule
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin
import org.testcontainers.containers.PostgreSQLContainer
import java.math.BigDecimal

class StepDefinitionsIT {

    companion object {
        private const val TRANSACTION_SERVICE_PORT = 8080
        private const val STATS_SERVICE_PORT = 8081
    }

    // Pair(expected, actual)
    private var results = setOf<Pair<String, String>>()

    private val httpClient = HttpClient()

    private lateinit var restateTestContainer: RestateTestContainer
    private var postgresContainer = PostgreSQLContainer("postgres:13")

    @Before
    fun setup() {
        startKoin { modules(
            transactionServiceModule,
            statsServiceModule,
            restServerDependenciesModule,
            restServerModule,
        ) }

        getKoin().get<EmbeddedServer<NettyApplicationEngine, NettyApplicationEngine.Configuration>>()
            .start()

        val transactionService = getKoin().get<TransactionService>()
        RestateHttpServer.listen(
            Endpoint
                .bind(transactionService)
            , TRANSACTION_SERVICE_PORT
        )

        val statsService = getKoin().get<StatsService>()
        RestateHttpServer.listen(
            Endpoint
                .bind(statsService)
            , STATS_SERVICE_PORT
        )

        postgresContainer.start()

        restateTestContainer = RestateTestContainer().apply { start() }

        // register the services first
        val port = restateTestContainer.getMappedPort(RestateTestContainer.DEPLOY_PORT)
        runBlocking {
            httpClient.post("http://localhost:$port/deployments") {
                contentType(ContentType.Application.Json)
                setBody("""{"uri": "http://host.docker.internal:$TRANSACTION_SERVICE_PORT"}""")
            }
            httpClient.post("http://localhost:$port/deployments") {
                contentType(ContentType.Application.Json)
                setBody("""{"uri": "http://host.docker.internal:$STATS_SERVICE_PORT"}""")
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
            dataTable.asMaps(String::class.java, String::class.java).map { row ->
                val expected = row["expected_response"] ?: ""
                val response = httpClient.post("http://localhost:9000/fee") {
                    contentType(ContentType.Application.Json)
                    val body = """{ "transactionId": "${row["transaction_id"] ?: ""}", "amount": "${BigDecimal(row["amount"])}", "asset": "${row["asset"]}" }"""
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