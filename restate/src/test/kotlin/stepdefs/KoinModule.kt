package stepdefs

import ivan.restate.service.DefaultRateCalculationPolicy
import ivan.restate.service.FiatRateCalculationPolicy
import ivan.restate.service.RateCalculationPolicy
import ivan.restate.service.RateCalculator
import java.math.BigDecimal

// DI for test setup
val appModule = org.koin.dsl.module {

    single<List<RateCalculationPolicy>> {
        listOf(
            FiatRateCalculationPolicy(BigDecimal.ONE),
            DefaultRateCalculationPolicy(BigDecimal.TWO)
        )
    }

    single { RateCalculator( get() ) }

}
