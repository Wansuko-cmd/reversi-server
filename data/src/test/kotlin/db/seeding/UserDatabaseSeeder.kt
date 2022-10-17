package db.seeding

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.transactions.transaction
import table.UserModel
import user.User
import user.UserId
import user.UserName

object UserDatabaseSeeder : DatabaseSeeder {
    override fun seeding(database: Database) {
        transaction(database) {
            UserModel.batchInsert(userData) {
                this[UserModel.id] = it.id.value
                this[UserModel.name] = it.name.value
            }
        }
    }

    private val userData = List(10) { index ->
        User.reconstruct(
            id = UserId("UserId$index"),
            name = UserName("UserName$index"),
        )
    }
}
