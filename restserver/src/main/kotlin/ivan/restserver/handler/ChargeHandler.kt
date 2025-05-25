package ivan.restserver.handler

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ivan.dto.rest.ChargeRequest
import ivan.dto.rest.ChargeResponse
import ivan.restserver.Config
import kotlinx.serialization.json.Json
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.UUID

class ChargeHandler(private val httpClient: HttpClient, private val config: Config) {

    private val logger: Logger = LoggerFactory.getLogger(ChargeHandler::class.java)

    suspend fun handle(call: RoutingCall) {
        val chargeRequest = call.receive<ChargeRequest>()
        logger.info("Received charge request from the REST endpoint: {}", chargeRequest)

        val transactionId = UUID.randomUUID().toString()
        val url = config.paymentServiceRestateUrlTemplate.replace("{transactionId}", transactionId)
        logger.info("Calling restate endpoint: {}, body: {}", url, Json.Default.encodeToString(chargeRequest))
        val response = httpClient.get(url) {
            contentType(ContentType.Application.Json)
            setBody(Json.Default.encodeToString(chargeRequest))
        }
        logger.info("Response from restate: {}, {}", response.status, response.bodyAsText())
        val str = response.bodyAsText()
        val chargeResponse = Json.Default.decodeFromString<ChargeResponse>(str)
        logger.info("Received charge response from backend: {}", chargeResponse)
        call.respond(chargeResponse)
    }

}