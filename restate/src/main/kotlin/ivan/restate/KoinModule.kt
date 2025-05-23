package ivan.restate

import ivan.restate.service.FeeService
import ivan.restate.dao.FeeRepository
import ivan.restate.service.RateCalculator
import ivan.restate.service.FiatRateCalculationPolicy
import org.koin.dsl.module
import java.math.BigDecimal

val appModule = module {

    single { Config() }
    single { FeeRepository(get()) }
    single {
        val rateCalculators = listOf(
            FiatRateCalculationPolicy(BigDecimal("0.0015"))
        )
        RateCalculator(rateCalculators)
    }
    single { FeeService(get(), get()) }

}
