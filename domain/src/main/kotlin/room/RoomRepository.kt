package room

import DomainException
import com.wsr.result.ApiResult

interface RoomRepository {
    suspend fun getAll(): ApiResult<List<Room>, DomainException>
    suspend fun getById(id: RoomId): ApiResult<Room, DomainException>
    suspend fun insert(room: Room): ApiResult<Unit, DomainException>
    suspend fun update(room: Room): ApiResult<Unit, DomainException>
}
