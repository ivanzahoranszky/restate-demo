package stepdefs

import ivan.transaction.service.DefaultRateCalculationPolicy
import ivan.transaction.service.FiatRateCalculationPolicy
import ivan.transaction.service.RateCalculationPolicy
import ivan.transaction.service.RateCalculator
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
