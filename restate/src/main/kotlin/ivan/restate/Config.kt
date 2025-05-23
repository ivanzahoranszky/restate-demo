package ivan.restate

import com.typesafe.config.ConfigFactory

class Config {

    val config = ConfigFactory.load()
    val jdbcUrl = config.getString("db.jdbcUrl")
    val username = config.getString("db.username")
    val password = config.getString("db.password")
    val maximumPoolSize = config.getInt("db.maximumPoolSize")

}