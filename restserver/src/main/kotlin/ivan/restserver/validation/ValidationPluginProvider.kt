package ivan.restserver.validation

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ivan.dto.rest.FeeRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ValidationPluginProvider(private val rootValidator: Validator<FeeRequest>) {

    val logger: Logger = LoggerFactory.getLogger(this.javaClass.name)

    fun get() = createApplicationPlugin("ValidationPlugin") {
        onCall { call ->
            when (call.request.uri) {
                "/transaction/fee" -> {
                    runCatching {
                        val feeRequest = call.receive<FeeRequest>()
                        rootValidator.validate(feeRequest)
                    }.onFailure {
                        logger.error(it.message ?: "Unknown error")
                        call.respond(HttpStatusCode.BadRequest, it.message ?: "")
                    }
                }
                "/payment/charge" -> {
                    // TODO
                }
                else -> { }
            }
        }
    }

}