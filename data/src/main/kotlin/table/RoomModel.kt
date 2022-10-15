package table

import org.jetbrains.exposed.sql.Table

object RoomModel : Table("rooms") {
    val id = varchar("id", 36)
    val black = varchar("black", 36) references UserModel.id
    val white = varchar("white", 36) references UserModel.id
    val next = integer("next").nullable()
    val board = board("board")
}
