package repository

import Cell
import DomainException
import com.wsr.result.ApiResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import room.Room
import room.RoomId
import room.RoomRepository
import table.RoomModel
import table.UserModel

class RoomRepositoryImpl(
    private val database: Database,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : RoomRepository {

    override suspend fun getAll(): ApiResult<List<Room>, DomainException> =
        runCatchWithTransaction(database, dispatcher) {
            RoomModel
                .selectAll()
                .map { it.toRoom(database) }
        }

    override suspend fun getById(id: RoomId): ApiResult<Room, DomainException> =
        runCatchWithTransaction(database, dispatcher) {
            RoomModel
                .select { RoomModel.id eq id.value }
                .first()
                .toRoom(db)
        }

    override suspend fun insert(room: Room): ApiResult<Unit, DomainException> =
        runCatchWithTransaction(database, dispatcher) {
            RoomModel.insert {
                it[black] = room.black.id.value
                it[white] = room.white.id.value
                it[next] = room.next?.toInt()
                it[board] = room.board
            }
        }

    override suspend fun update(room: Room): ApiResult<Unit, DomainException> =
        runCatchWithTransaction(database, dispatcher) {
            RoomModel
                .update(
                    where = { RoomModel.id eq room.id.value },
                    limit = 1,
                ) {
                    it[black] = room.black.id.value
                    it[white] = room.white.id.value
                    it[next] = room.next?.toInt()
                    it[board] = room.board
                }
        }
}

private fun ResultRow.toRoom(db: Database) = Room.reconstruct(
    id = RoomId(this[RoomModel.id]),
    black = transaction(db) { UserModel.select { UserModel.id eq this@toRoom[RoomModel.black] } }.first().toUser(),
    white = transaction(db) { UserModel.select { UserModel.id eq this@toRoom[RoomModel.white] } }.first().toUser(),
    next = this[RoomModel.next]?.let(Cell.Piece::from),
    board = this[RoomModel.board],
)
