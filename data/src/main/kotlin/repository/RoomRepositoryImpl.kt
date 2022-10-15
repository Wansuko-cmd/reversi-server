package repository

import Cell
import DomainException
import com.wsr.result.ApiResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
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
        withContext(dispatcher) {
            transaction(database) {
                RoomModel
                    .selectAll()
                    .map { it.toRoom(database) }
                    .let { ApiResult.Success(it) }
            }
        }

    override suspend fun getById(id: RoomId): ApiResult<Room, DomainException> =
        withContext(dispatcher) {
            transaction(database) {
                RoomModel
                    .select { RoomModel.id eq id.value }
                    .firstOrNull()
                    ?.toRoom(db)
                    ?.let { ApiResult.Success(it) }
                    ?: ApiResult.Failure(DomainException.NoSuchElementException("Room"))
            }
        }

    override suspend fun update(room: Room): ApiResult<Unit, DomainException> =
        withContext(dispatcher) {
            transaction(database) {
                RoomModel
                    .update(
                        where = { RoomModel.id eq room.id.value },
                        limit = 1,
                    ) {
                        it[black] = room.black.id.value
                        it[white] = room.white.id.value
                        it[next] = room.next?.toInt()
                        it[board] = board
                    }
                ApiResult.Success(Unit)
            }
        }
}

private fun ResultRow.toRoom(db: Database) = Room.reconstruct(
    id = RoomId(this[RoomModel.id]),
    black = transaction(db) { UserModel.select { UserModel.id eq this@toRoom[RoomModel.black] } }.first().toUser(),
    white = transaction(db) { UserModel.select { UserModel.id eq this@toRoom[RoomModel.white] } }.first().toUser(),
    next = this[RoomModel.next]?.let(Cell.Piece::from),
    board = this[RoomModel.board]
)
