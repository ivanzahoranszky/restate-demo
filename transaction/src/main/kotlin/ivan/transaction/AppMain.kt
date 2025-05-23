package ivan.transaction

import dev.restate.sdk.endpoint.Endpoint
import dev.restate.sdk.http.vertx.RestateHttpServer
import ivan.transaction.service.TransactionService
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin

object AppMain {

    @JvmStatic
    fun main(args: Array<String>) {

        startKoin { modules(transactionServiceModule) }

        val transactionService = getKoin().get<TransactionService>()
        RestateHttpServer.listen(
            Endpoint
                .bind(transactionService)
        )

    }

}