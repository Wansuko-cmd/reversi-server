package db.seeding

import org.jetbrains.exposed.sql.Database

interface DatabaseSeeder {
    fun seeding(database: Database)
}

val seeding = listOf(UserDatabaseSeeder, RoomDatabaseSeeder)

fun Database.seeding() = seeding.forEach { it.seeding(database = this) }
