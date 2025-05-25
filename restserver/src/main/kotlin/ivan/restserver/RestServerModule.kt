package ivan.restserver

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.doublereceive.*
import io.ktor.server.routing.*
import ivan.restserver.handler.ChargeHandler
import ivan.restserver.handler.FeeHandler
import ivan.restserver.validation.ValidationPluginProvider
import org.koin.dsl.module

val restServerModule = module {

    single {
        embeddedServer(Netty, port = 9000) {
            install(DoubleReceive)
            install(ContentNegotiation) {
                json()
            }
            install(get<ValidationPluginProvider>().get())
            routing {
                post("transaction/fee") {
                    get<FeeHandler>().handle(call)
                }
                post("payment/charge") {
                    get<ChargeHandler>().handle(call)
                }
            }
        }
    }

}
