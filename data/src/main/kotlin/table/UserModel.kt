package table

import org.jetbrains.exposed.sql.Table

object UserModel : Table("users") {
    val id = varchar("id", 36)
    val name = text("name")
    val userStatus = varchar("room_id", 36).nullable()

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}
