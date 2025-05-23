package ivan.restserver


import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.doublereceive.*
import io.ktor.server.routing.*
import ivan.restserver.validation.ValidationPluginProvider
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.LoggerFactory

val logger = LoggerFactory.getLogger(Application::class.java)

fun main() {
    startKoin { modules(appModule) }

    val restHandler = getKoin().get<RestHandler>()
    val validationPluginProvider = getKoin().get<ValidationPluginProvider>()

    embeddedServer(Netty, port = 9000) {
        install(DoubleReceive)
        install(ContentNegotiation) {
            json()
        }
        install(validationPluginProvider.get())
        routing {
            post("/fee") {
                restHandler.handle(call)
            }
        }
    }.start(wait = true)

}