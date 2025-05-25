package ivan.account

import ivan.account.service.AccountService
import org.koin.dsl.module

val statsServiceModule = module {

    single { Config() }

    single { AccountService( get() ) }

}
