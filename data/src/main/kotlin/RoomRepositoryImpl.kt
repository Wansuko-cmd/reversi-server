import room.Room
import room.RoomId
import room.RoomRepository
import user.User
import user.UserId
import user.UserName

object RoomRepositoryImpl : RoomRepository {
    private val rooms = mutableListOf<Room>(
        Room.create(User(UserId(""), UserName(""),), User(UserId(""), UserName(""),))
    )

    override suspend fun getAll(): List<Room> = rooms

    override suspend fun getById(id: RoomId): Room = rooms.first { it.id == id }

    override suspend fun upsert(room: Room) {
        rooms.add(room)
    }
}
