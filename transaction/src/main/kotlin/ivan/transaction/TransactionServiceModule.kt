package ivan.transaction

import ivan.transaction.service.TransactionService
import ivan.transaction.dao.FeeRepository
import ivan.transaction.service.RateCalculator
import ivan.transaction.service.FiatRateCalculationPolicy
import org.koin.dsl.module
import java.math.BigDecimal

val transactionServiceModule = module {

    single { Config() }
    single { FeeRepository(get()) }
    single {
        val rateCalculators = listOf(
            FiatRateCalculationPolicy(BigDecimal("0.0015"))
        )
        RateCalculator(rateCalculators)
    }
    single { TransactionService(get(), get()) }

}
