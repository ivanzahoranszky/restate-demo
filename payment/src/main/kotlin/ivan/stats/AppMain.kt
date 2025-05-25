package ivan.stats

import dev.restate.sdk.endpoint.Endpoint
import dev.restate.sdk.http.vertx.RestateHttpServer
import ivan.stats.service.PaymentWorkflow
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin

object AppMain {

    @JvmStatic
    fun main(args: Array<String>) {
        startKoin { modules(paymentWorkflowModule) }

        val statsService = getKoin().get<PaymentWorkflow>()
        RestateHttpServer.listen(
            Endpoint
                .bind(statsService)
        )
    }

}