package table

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import user.User
import user.UserId
import user.UserName

object UserModel : Table("users") {
    val id = varchar("id", 36)
    val name = text("name")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}