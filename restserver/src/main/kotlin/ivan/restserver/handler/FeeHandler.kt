package ivan.restserver.handler

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingCall
import ivan.dto.rest.FeeRequest
import ivan.dto.rest.FeeRespone
import ivan.restserver.Config
import kotlinx.serialization.json.Json
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class FeeHandler(private val httpClient: HttpClient, private val config: Config) {

    private val logger: Logger = LoggerFactory.getLogger(FeeHandler::class.java)

    suspend fun handle(call: RoutingCall) {
        val feeRequest = call.receive<FeeRequest>()
        logger.info("Received fee request from the REST endpoint: {}", feeRequest)
        logger.info("Calling restate endpoint: {}, body: {}", config.transactionServiceRestateUrl, Json.Default.encodeToString(feeRequest))
        val response = httpClient.get(config.transactionServiceRestateUrl) {
            contentType(ContentType.Application.Json)
            setBody(Json.Default.encodeToString(feeRequest))
        }
        logger.info("Response from restate: {}, {}", response.status, response.bodyAsText())
        val str = response.bodyAsText()
        val feeResponse = Json.Default.decodeFromString<FeeRespone>(str)
        logger.info("Received fee response from backend: {}", feeResponse)
        call.respond(feeResponse)
    }

}