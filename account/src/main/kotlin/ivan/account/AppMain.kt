package ivan.account

import dev.restate.sdk.endpoint.Endpoint
import dev.restate.sdk.http.vertx.RestateHttpServer
import ivan.account.service.AccountService
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin

fun main() {

    startKoin { modules(statsServiceModule) }

    val statsService = getKoin().get<AccountService>()
    RestateHttpServer.listen(
        Endpoint
            .bind(statsService)
    )

}