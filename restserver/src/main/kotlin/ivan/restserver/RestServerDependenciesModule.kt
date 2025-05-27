package ivan.restserver

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import ivan.dto.rest.FeeRequest
import ivan.restserver.handler.ChargeHandler
import ivan.restserver.handler.FeeHandler
import ivan.restserver.validation.AmountValidator
import ivan.restserver.validation.AssetValidator
import ivan.restserver.validation.ValidationPluginProvider
import ivan.restserver.validation.Validator
import org.koin.dsl.module

val restServerDependenciesModule = module {

    single { Config() }

    single {
        FeeHandler(
            httpClient = get(),
            config = get()
        )
    }

    single {
        ChargeHandler(
            httpClient = get(),
            config = get()
        )
    }

    single {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json()
            }
        }
    }

    // provides the validator chain
    single<Validator<FeeRequest>> {
        val assetValidator = AssetValidator(null)
        AmountValidator(assetValidator)
    }

    single {
        ValidationPluginProvider(get())
    }

}
