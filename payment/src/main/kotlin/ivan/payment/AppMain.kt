package ivan.payment

import dev.restate.sdk.endpoint.Endpoint
import dev.restate.sdk.http.vertx.RestateHttpServer
import ivan.payment.service.PaymentWorkflow
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin

fun main() {

    startKoin { modules(paymentWorkflowModule) }

    val statsService = getKoin().get<PaymentWorkflow>()
    RestateHttpServer.listen(
        Endpoint
            .bind(statsService)
    )

}
