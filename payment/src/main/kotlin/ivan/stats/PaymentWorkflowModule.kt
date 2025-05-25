package ivan.stats

import ivan.stats.service.PaymentWorkflow
import org.koin.dsl.module

val paymentWorkflowModule = module {

    single { Config() }

    single { PaymentWorkflow( get() ) }

}
