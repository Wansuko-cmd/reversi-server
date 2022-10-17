import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import db.seeding.seeding
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import javax.sql.DataSource

val TestDB: Database = run {
    val pool = hikari()
    Database.connect(pool)
        .also { pool.migrate() }
        .also { it.seeding() }
}

private fun hikari(): HikariDataSource {
    val config = HikariConfig().apply {
        driverClassName = "org.h2.Driver"
        jdbcUrl = "jdbc:h2:mem:dev_db;DB_CLOSE_DELAY=-1"
        username = ""
        password = ""
        maximumPoolSize = 3
        isAutoCommit = false
        validate()
    }
    return HikariDataSource(config)
}

fun DataSource.migrate() {
    val flyway = Flyway.configure()
        .dataSource(this)
        .load()

    flyway.info()
    flyway.migrate()
}
