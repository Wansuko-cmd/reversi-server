package db.seeding

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.transactions.transaction
import room.Room
import room.RoomId
import table.RoomModel
import user.User
import user.UserId
import user.UserName
import user.UserStatus

object RoomDatabaseSeeder : DatabaseSeeder {
    override fun seeding(database: Database) {
        transaction(database) {
            RoomModel.batchInsert(roomData) {
                this[RoomModel.id] = it.id.value
                this[RoomModel.black] = it.black.id.value
                this[RoomModel.white] = it.white.id.value
                this[RoomModel.next] = it.next?.toInt()
                this[RoomModel.board] = it.board
            }
        }
    }

    private val roomData = List(5) { index ->
        Room.reconstruct(
            id = RoomId("RoomId$index"),
            black = User.reconstruct(
                id = UserId("UserId$index"),
                name = UserName("UserName$index"),
                status = UserStatus.OnMatch(roomId = RoomId("RoomId$index")),
            ),
            white = User.reconstruct(
                id = UserId("UserId$index"),
                name = UserName("UserName$index"),
                status = UserStatus.OnMatch(roomId = RoomId("RoomId$index")),
            ),
            next = Cell.Piece.Black,
            board = Board.create(20),
        )
    }
}
