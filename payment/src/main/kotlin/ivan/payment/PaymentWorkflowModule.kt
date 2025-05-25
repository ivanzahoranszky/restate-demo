package ivan.payment

import ivan.account.Config
import ivan.payment.service.PaymentWorkflow
import org.koin.dsl.module

val paymentWorkflowModule = module {

    single { Config() }

    single { PaymentWorkflow( get() ) }

}
