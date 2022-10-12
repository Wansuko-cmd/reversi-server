package room

interface RoomRepository {
    suspend fun getAll(): List<Room>
    suspend fun getById(id: RoomId): Room
    suspend fun upsert(room: Room)
}