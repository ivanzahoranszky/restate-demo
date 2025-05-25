package ivan.restserver

import com.typesafe.config.ConfigFactory

class Config {

    val config = ConfigFactory.load()
    val transactionServiceRestateUrl = config.getString("service.transaction.restate-url")
    val paymentServiceRestateUrl = config.getString("service.payment.restate-url")

}