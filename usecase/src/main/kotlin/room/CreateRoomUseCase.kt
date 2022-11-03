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
                .map { rooms ->
                    rooms.map {
                        userRepository.update(it.black)
                        userRepository.update(it.white)
                    }.flatMap { rooms }
                }
                .flatMap { users ->
                    users.map { roomRepository.insert(it) }
                        .sequence()
                        .map { }
                }
        }

    private fun List<User>.takeTwoUsers(): ApiResult<List<Pair<User, User>>, DomainException> =
        try {
            this.shuffled()
                .chunked(2) { users -> users[0] to users[1] }
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
}
