package db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import javax.sql.DataSource

val ProdDB: Database by lazy {
    val pool = hikari()
    Database.connect(pool)
        .also { pool.migrate() }
}

private fun hikari(): HikariDataSource {
    val config = HikariConfig().apply {
        driverClassName = "org.postgresql.Driver"
        jdbcUrl = "jdbc:postgresql://localhost:5432/reversivsai"
        username = ""
        password = ""
        maximumPoolSize = MAXIMUM_POOL_SIZE
        isAutoCommit = false
        validate()
    }
    return HikariDataSource(config)
}

private fun DataSource.migrate() {
    val flyway = Flyway.configure()
        .dataSource(this)
        .load()

    flyway.info()
    flyway.migrate()
}

private const val MAXIMUM_POOL_SIZE = 3
