package ivan.stats

import dev.restate.sdk.endpoint.Endpoint
import dev.restate.sdk.http.vertx.RestateHttpServer
import ivan.stats.service.StatsService
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin


fun main() {

    startKoin { modules(statsServiceModule) }

    val statsService = getKoin().get<StatsService>()
    RestateHttpServer.listen(
        Endpoint
            .bind(statsService)
    )

}
