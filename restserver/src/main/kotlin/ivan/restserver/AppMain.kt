package ivan.restserver

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin

fun main() {

    startKoin { modules(restServerDependenciesModule, restServerModule) }
    getKoin().get<EmbeddedServer<NettyApplicationEngine, NettyApplicationEngine.Configuration>>()
        .start(wait = true)

}