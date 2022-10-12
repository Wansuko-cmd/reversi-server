package room

interface RoomRepository {
    fun getAll(): List<Room>
    fun getById(id: RoomId): Room
    fun upsert(room: Room)
}