package room

import DomainException
import com.wsr.result.ApiResult
import com.wsr.result.flatMap
import com.wsr.result.map
import user.UserId

class UpdateRoomUseCase(
    private val roomRepository: RoomRepository,
) {
    suspend operator fun invoke(
        roomId: RoomId,
        row: Int,
        column: Int,
        userId: UserId,
    ): ApiResult<Unit, DomainException> = roomRepository
        .getById(roomId)
        .checkIsNextUser(userId)
        .flatMap { it.place(row, column) }
        .map { room -> roomRepository.upsert(room) }

    private fun ApiResult<Room, DomainException>.checkIsNextUser(
        userId: UserId,
    ): ApiResult<Room, DomainException> = this.flatMap { room ->
        if (room.isNextUser(userId)) ApiResult.Success(room)
        else ApiResult.Failure(DomainException.ValidationException("You're not next user."))
    }
}
