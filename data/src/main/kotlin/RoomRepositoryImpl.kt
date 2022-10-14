import com.wsr.result.ApiResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import room.Room
import room.RoomId
import room.RoomRepository
import user.User
import user.UserId
import user.UserName

class RoomRepositoryImpl(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : RoomRepository {
    private val rooms = mutableListOf<Room>(
        Room.create(
            User(UserId("UserId1"), UserName("UserName1")),
            User(UserId("UserId2"), UserName("UserName2")),
        )
    )

    override suspend fun getAll(): ApiResult<List<Room>, DomainException> =
        withContext(dispatcher) {
            ApiResult.Success(rooms)
        }

    override suspend fun getById(id: RoomId): ApiResult<Room, DomainException> =
        withContext(dispatcher) {
            rooms.firstOrNull { it.id == id }
                ?.let { ApiResult.Success(it) }
                ?: ApiResult.Failure(DomainException.NoSuchElementException("Room"))
        }

    override suspend fun upsert(room: Room): ApiResult<Unit, DomainException> =
        withContext(dispatcher) {
            rooms.add(room)
            ApiResult.Success(Unit)
        }
}
