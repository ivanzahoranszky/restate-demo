package ivan.restserver

import com.typesafe.config.ConfigFactory

class Config {

    val config = ConfigFactory.load()
    val restserverRestateUrl = config.getString("restserver.restate.url")

}