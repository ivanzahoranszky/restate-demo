package ivan.stats

import com.typesafe.config.ConfigFactory

class Config {

    val config = ConfigFactory.load()
    val logMessage = config.getString("log-message")

}