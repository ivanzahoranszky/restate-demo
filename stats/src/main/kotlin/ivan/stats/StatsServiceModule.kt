package ivan.stats

import ivan.stats.service.StatsService
import org.koin.dsl.module

val statsServiceModule = module {

    single { Config() }

    single { StatsService( get() ) }

}
