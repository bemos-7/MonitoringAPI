package infrastructure.database.config

import org.jetbrains.exposed.sql.Database

object DatabaseConfig {
    fun connect() {
        Database.connect("jdbc:sqlite:ESP8266Config.db", driver = "org.sqlite.JDBC")
        println("Database connected successfully!")
    }
}