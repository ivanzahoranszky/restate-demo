package ivan.restserver

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ivan.dto.rest.FeeRequest
import ivan.dto.rest.FeeRespone
import kotlinx.serialization.json.Json
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class RestHandler(private val httpClient: HttpClient, private val config: Config) {

    private val logger: Logger = LoggerFactory.getLogger(RestHandler::class.java)

    suspend fun handle(call: RoutingCall) {
        val feeRequest = call.receive<FeeRequest>()
        logger.info("Received fee request from the REST endpoint: {}", feeRequest)
        logger.info("Calling restate endpoint: {}, body: {}", config.restserverRestateUrl, Json.encodeToString(feeRequest))
        val response = httpClient.get(config.restserverRestateUrl) {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(feeRequest))
        }
        logger.info("Response from restate: {}, {}", response.status, response.bodyAsText())
        val str = response.bodyAsText()
        val feeResponse = Json.decodeFromString<FeeRespone>(str)
        logger.info("Received fee response from backend: {}", feeResponse)
        call.respond(feeResponse)
    }

}
