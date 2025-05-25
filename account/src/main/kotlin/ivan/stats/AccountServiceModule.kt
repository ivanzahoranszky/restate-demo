package ivan.stats

import ivan.stats.service.AccountService
import org.koin.dsl.module

val statsServiceModule = module {

    single { Config() }

    single { AccountService( get() ) }

}
