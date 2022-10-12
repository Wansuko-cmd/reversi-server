import room.Room
import room.RoomId
import room.RoomRepository

object RoomRepositoryImpl : RoomRepository {
    private val rooms = mutableListOf<Room>()

    override suspend fun getAll(): List<Room> = rooms

    override suspend fun getById(id: RoomId): Room = rooms.first { it.id == id }

    override suspend fun upsert(room: Room) {
        rooms.add(room)
    }
}
