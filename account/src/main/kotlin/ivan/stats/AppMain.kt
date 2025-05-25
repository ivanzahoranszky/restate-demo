package ivan.stats

import dev.restate.sdk.endpoint.Endpoint
import dev.restate.sdk.http.vertx.RestateHttpServer
import ivan.stats.service.AccountService
import ivan.stats.service.StatsService
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin

object AppMain {

    @JvmStatic
    fun main(args: Array<String>) {
        startKoin { modules(statsServiceModule) }

        val statsService = getKoin().get<AccountService>()
        RestateHttpServer.listen(
            Endpoint
                .bind(statsService)
        )
    }

}