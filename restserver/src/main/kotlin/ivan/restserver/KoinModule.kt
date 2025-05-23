package ivan.restserver

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import ivan.dto.FeeRequest
import ivan.restserver.validation.AmountValidator
import ivan.restserver.validation.AssetValidator
import ivan.restserver.validation.ValidationPluginProvider
import ivan.restserver.validation.Validator
import org.koin.dsl.module

val appModule = module {

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

    // provides the root input validator
    single<Validator<FeeRequest>> {
        val assetValidator = AssetValidator(null)
        AmountValidator(assetValidator)
    }

    single {
        ValidationPluginProvider(get())
    }

}
