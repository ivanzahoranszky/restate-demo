package ivan.restserver.validation

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ivan.dto.rest.FeeRequest

class ValidationPluginProvider(private val rootValidator: Validator<FeeRequest>) {

    fun get() = createApplicationPlugin("ValidationPlugin") {
        onCall { call ->
            when (call.request.uri) {
                "/transaction/fee" -> {
                    val feeRequest = call.receive<FeeRequest>()
                    runCatching {
                        rootValidator.validate(feeRequest)
                        println()
                    }.onFailure {
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