package ivan.restserver

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import ivan.dto.rest.FeeRequest
import ivan.restserver.validation.AmountValidator
import ivan.restserver.validation.AssetTypeValidator
import ivan.restserver.validation.ValidationPluginProvider
import ivan.restserver.validation.Validator
import org.koin.dsl.module

val restServerDependenciesModule = module {

    single { Config() }

    single { HttpClient(CIO) {
            install(ContentNegotiation) {
                json()
            }
        }
    }

    single { RestHandler(
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
        val assetTypeValidator = AssetTypeValidator(null)
        AmountValidator(assetTypeValidator)
    }

    single {
        ValidationPluginProvider(get())
    }

}
