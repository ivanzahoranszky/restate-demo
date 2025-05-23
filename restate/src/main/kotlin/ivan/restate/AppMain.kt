package ivan.restate

import dev.restate.sdk.endpoint.Endpoint
import dev.restate.sdk.http.vertx.RestateHttpServer
import ivan.restate.dao.FeeRepository
import ivan.restate.service.FeeService
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin

object AppMain {

    @JvmStatic
    fun main(args: Array<String>) {
        startKoin { modules(appModule) }

        val feeService = getKoin().get<FeeService>()
        RestateHttpServer.listen(
            Endpoint
                .bind(feeService)
        )
    }

}