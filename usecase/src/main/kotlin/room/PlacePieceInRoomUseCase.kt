package room

import DomainException
import com.wsr.result.ApiResult
import com.wsr.result.flatMap
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import user.UserId
import user.UserRepository
import user.UserStatus

class PlacePieceInRoomUseCase(
    private val roomRepository: RoomRepository,
    private val userRepository: UserRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {
    suspend operator fun invoke(
        roomId: RoomId,
        row: Int,
        column: Int,
        userId: UserId,
    ): ApiResult<Unit, DomainException> = withContext(dispatcher) {
        roomRepository
            .getById(roomId)
            .checkIsNextUser(userId)
            .flatMap { it.place(row, column) }
            .updateUserIfFinished()
            .flatMap { room -> roomRepository.update(room) }
    }

    private fun ApiResult<Room, DomainException>.checkIsNextUser(
        userId: UserId,
    ): ApiResult<Room, DomainException> = this.flatMap { room ->
        if (room.isNextUser(userId)) ApiResult.Success(room)
        else ApiResult.Failure(DomainException.RequestValidationException("You're not next user."))
    }

    private suspend fun ApiResult<Room, DomainException>.updateUserIfFinished(): ApiResult<Room, DomainException> =
        this.flatMap { room ->
            if (room.next == null) {
                userRepository.update(room.black.updateStatus(UserStatus.WaitMatting))
                userRepository.update(room.white.updateStatus(UserStatus.WaitMatting))
            }
            ApiResult.Success(room)
        }
}
