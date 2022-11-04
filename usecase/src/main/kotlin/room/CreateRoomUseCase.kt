package room

import DomainException
import com.wsr.result.ApiResult
import com.wsr.result.flatMap
import com.wsr.result.map
import com.wsr.result.sequence
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import user.User
import user.UserRepository
import user.UserStatus

class CreateRoomUseCase(
    private val roomRepository: RoomRepository,
    private val userRepository: UserRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {
    suspend operator fun invoke(): ApiResult<Unit, DomainException> =
        withContext(dispatcher) {
            userRepository
                .getAll()
                .map { users -> users.filter { it.status is UserStatus.WaitMatting } }
                .flatMap { users -> users.takeTwoUsers() }
                .map { users -> users.map { Room.create(it.first, it.second) } }
                .insertRooms()
                .updateUsers()
                .map { Unit }
        }

    private fun List<User>.takeTwoUsers(): ApiResult<List<Pair<User, User>>, DomainException> =
        try {
            this.shuffled()
                .windowed(size = 2, step = 2) { users -> users[0] to users[1] }
                .let { ApiResult.Success(it) }
        } catch (e: IndexOutOfBoundsException) {
            ApiResult.Failure(
                DomainException.NoMoreWaitMattingUserException(
                    message = "There isn't match user waiting matting here.",
                ),
            )
        } catch (e: Exception) {
            ApiResult.Failure(DomainException.SystemException("", e))
        }

    private suspend fun ApiResult<List<Room>, DomainException>.insertRooms(): ApiResult<List<Room>, DomainException> =
        this.flatMap { rooms ->
            rooms.map { roomRepository.insert(it) }
                .sequence()
                .map { rooms }
        }

    private suspend fun ApiResult<List<Room>, DomainException>.updateUsers(): ApiResult<List<Room>, DomainException> =
        this.map { rooms ->
            rooms.map {
                userRepository.update(it.black.updateStatus(UserStatus.OnMatch(it.id)))
                userRepository.update(it.white.updateStatus(UserStatus.OnMatch(it.id)))
            }.flatMap { rooms }
        }
}
